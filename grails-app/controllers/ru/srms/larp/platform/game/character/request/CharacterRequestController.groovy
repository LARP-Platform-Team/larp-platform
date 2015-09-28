package ru.srms.larp.platform.game.character.request

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.CharacterRequestService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class CharacterRequestController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]
  CharacterRequestService characterRequestService

  def index() {

  }

  def create() {
    withModule {
      respond characterRequestService.createRequest(params.game, params)
    }
  }

  def edit(CharacterRequest request) {
    withModule {
      respond characterRequestService.editRequest(request)
    }
  }

  @Transactional
  def save() {
    CharacterRequest request = characterRequestService.composeRequest(params.game, params)
    request.status = params.containsKey('sendCharacterRequest') ?
        RequestStatus.SENT : RequestStatus.DRAFT;
    withModule {
      if (validateData(request, 'create')) {
        characterRequestService.saveRequest(request)
        respondChange('Заявка успешно создана', CREATED, [controller: 'Game'])
      }
    }
  }

  @Transactional
  def update(CharacterRequest request) {
    withModule {
      field.sortOrder = 0
      if (validateData(field, 'edit')) {
        characterRequestService.saveField(request)
        respondChange('Поле обновлено', OK, redirectRoute(field))
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.REQUEST_FORM
  }

}
