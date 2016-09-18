package ru.srms.larp.platform.game.character.request

import grails.plugin.htmlcleaner.HtmlCleaner
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.security.access.AccessDeniedException
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.CharacterRequestService
import ru.srms.larp.platform.CharacterService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.game.roles.GameRole

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class CharacterRequestController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]
  CharacterRequestService characterRequestService
  CharacterService characterService
  HtmlCleaner htmlCleaner


  @Override
  public Map getBreadcrumbDescriptors() {
    // TODO 'show' should point to index or game depends on GM or not looks
    [(Descriptor.DEFAULT_KEY): Descriptor.root()]
  }

  def index() {
    withModule {
      respond characterRequestService.listRequests(params.game, params.getBoolean('all'), paginator()),
          model: [itemsQty: characterRequestService.countRequests(params.game, params.getBoolean('all'))]
    }
  }

  def show(CharacterRequest request) {
    withModule {
      respond characterRequestService.showRequest(request), model: generateCommonModel(request, true)
    }
  }

  def create() {
    withModule {
      def characterRequest = characterRequestService.createRequest(params.game)
      respond characterRequest, model: generateCommonModel(characterRequest)
    }
  }

  def edit(CharacterRequest request) {
    withModule {
      if (!request.status.data.editable)
        throw new AccessDeniedException("Запрещено редактировать в данном состоянии")
      respond characterRequestService.editRequest(request), model: generateCommonModel(request)
    }
  }

  @Transactional
  def save() {
    CharacterRequest request = characterRequestService.createRequest(params.game)
    fillRequestWithData(request)
    request.status = params.containsKey('sendCharacterRequest') ?
        RequestStatus.SENT : RequestStatus.DRAFT;
    withModule {
      if (validateData(request, 'create', generateCommonModel(request))) {
        characterRequestService.saveRequest(request)
        respondChange('Заявка успешно создана', CREATED, [mapping: 'game'])
      }
    }
  }

  @Transactional
  def update() {
    withModule {
      CharacterRequest request = CharacterRequest.get(params.id)
      if (!request.status.data.editable)
        throw new AccessDeniedException("Запрещено редактировать в данном состоянии")

      fillRequestWithData(request)
      request.status = params.containsKey('sendCharacterRequest') ?
          RequestStatus.SENT : RequestStatus.DRAFT;
      if (validateData(request, 'edit', generateCommonModel(request))) {
        characterRequestService.updateRequest(request)
        respondChange('Заявка обновлена', OK, [mapping: 'game'])
      }
    }
  }

  @Transactional
  def changeState() {
    withModule {
      if (!params.request_new_status)
        throw new RuntimeException("No new status defined")

      CharacterRequest request = CharacterRequest.get(params.id)
      RequestStatus newStatus = RequestStatus.valueOf(params.request_new_status)
      if (!request.status.data.canChangeTo(newStatus))
        throw new AccessDeniedException("New status is not permitted")

      def oldCharacter = null
      if (request.status.data.selectCharacter && params.request_character_id) {
        oldCharacter = request.character
        def newCharacter = GameCharacter.get(params.getLong('request_character_id'))
        request.character = newCharacter
      }

      request.status = newStatus
      if (params.request_comment)
        request.comment = htmlCleaner.cleanHtml(params.request_comment, 'simple-rich-text')

      if (validateData(request, 'change-state')) {
        characterRequestService.changeStatus(request)
        // change previously assigned character
        if (oldCharacter)
          characterService.save(oldCharacter, null)
        // update recently assigned character
        if (request.character) {
          def oldPlayer = request.character.player
          request.character.player = request.user
          characterService.save(request.character, oldPlayer)
        }
        respondChange('Состояние изменено', OK, RequestStatus.REVIEW.equals(newStatus) ?
            [mapping: 'gameRequest', id: request.id, action: 'show'] : [mapping: 'gameRequest', action: 'index']
        )
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.REQUEST_FORM
  }

  private def generateCommonModel(CharacterRequest request, forShow = false) {
    [
            gameFields: gameFields(),
            roles     : rolesWithFields(forShow ? request.roles : null),
            values    : makeValuesHashmap(request)
    ]
  }

  private Map<Long, FormFieldValue> makeValuesHashmap(CharacterRequest request) {
    !request.values ? [:] :
        request.values.collectEntries { [(it.fieldId): it] }
  }

  private List<RequestFormField> gameFields() {
    RequestFormField.findAllByParent(params.game.wrapper)
  }

  private Map<GameRole, List<RequestFormField>> rolesWithFields(def requestRoles = null) {
    def roles = requestRoles ?: GameRole.findAllByGameAndRequestAvailable(params.game, true)
    roles.collectEntries {
      [(it): (it.wrapper ?
          RequestFormField.findAllByParent(it.wrapper) : [])]
    }
  }

  private void fillRequestWithData(CharacterRequest request) {
    def prevRoles = new HashSet<GameRole>(request.roles ?: [])
    if (params.roles)
      request.properties['roles'] = params
    else
      request.roles = []
    def valuesHashmap = makeValuesHashmap(request)

    // берем роли, которые были удалены при обновлении
    prevRoles.findAll { !request.roles.contains(it) }
        .each { role ->
      // выбираем все поля, связанные с этими ролями
      RequestFormField.findAllByParent(role.wrapper).each { field ->
        // и удаляем из анкеты значения этих полей
        if (valuesHashmap.containsKey(field.id))
          request.removeFromValues(valuesHashmap.get(field.id))
      }
    }

    // формируем пулл полей: общие игровые + для конкретных ролей
    def fields = gameFields()
    request.roles.each { role ->
      if (RequestFormField.countByParent(role.wrapper))
        fields.addAll(RequestFormField.findAllByParent(role.wrapper))
    }

    // обновляем значения полей
    fields.each { field ->

      FormFieldValue fieldValue;
      if (!valuesHashmap.containsKey(field.id)) {
        fieldValue = new FormFieldValue(field: field)
        request.addToValues(fieldValue)
      } else
        fieldValue = valuesHashmap.get(field.id)

      String fieldId = "request_from_field_${field.id}"
      fieldValue.value = fieldValue.field.type.transform(
          [value: params.get(fieldId), cleaner: htmlCleaner])
    }
  }

}
