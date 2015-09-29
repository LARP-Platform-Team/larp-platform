package ru.srms.larp.platform.game.character.request

import grails.plugin.htmlcleaner.HtmlCleaner
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.security.access.AccessDeniedException
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.CharacterRequestService
import ru.srms.larp.platform.CharacterService
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class CharacterRequestController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]
  CharacterRequestService characterRequestService
  CharacterService characterService
  HtmlCleaner htmlCleaner

  def index() {
    withModule {
      respond characterRequestService.listRequests(params.game, params.getBoolean('all'), paginator()),
          model: [itemsQty: characterRequestService.countRequests(params.game, params.getBoolean('all'))]
    }
  }

  def show(CharacterRequest request) {
    withModule {
      respond characterRequestService.showRequest(request)
    }
  }

  def create() {
    withModule {
      def characterRequest = characterRequestService.createRequest(params.game)
      fillRequestWithData(characterRequest)
      respond characterRequest
    }
  }

  def edit(CharacterRequest request) {
    withModule {
      if (!request.status.data.editable)
        throw new AccessDeniedException("Рапрещено редактировать в данном состоянии")
      respond characterRequestService.editRequest(request)
    }
  }

  @Transactional
  def save() {
    CharacterRequest request = characterRequestService.createRequest(params.game)
    fillRequestWithData(request)
    request.status = params.containsKey('sendCharacterRequest') ?
        RequestStatus.SENT : RequestStatus.DRAFT;
    withModule {
      if (validateData(request, 'create')) {
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
      if (validateData(request, 'edit')) {
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

  private void fillRequestWithData(CharacterRequest request) {
    // create field values for a new request
    if (!request.id) {
      RequestFormField.findAllByParent(params.game.wrapper)
          .findAll { it.type.isInput() }.each {
        request.addToValues(new FormFieldValue(field: it))
      }
    }

    // save values
    request.values.each {
      String fieldId = "request_from_field_${it.field.id}"
      it.value = it.field.type.transform([value: params[fieldId], cleaner: htmlCleaner])
    }
  }

}
