package ru.srms.larp.platform.game

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.CharacterRequestService
import ru.srms.larp.platform.GameService
import ru.srms.larp.platform.exceptions.AjaxException
import ru.srms.larp.platform.sec.SpringUser

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameController extends BaseController {

  static allowedMethods = [save: "POST", update: "POST"]
  GameService gameService
  CharacterRequestService characterRequestService

  @Secured(['permitAll'])
  def index(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    respond Game.list(params), model: [gameInstanceCount: Game.count()]
  }

  @Secured(['permitAll'])
  def play() {
    respond params.game,
        model: [
            characters: gameService.getAvailableCharacters(params.game),
            requests: characterRequestService.findForCurrentUser(params.game)
        ]
  }

  def create() {
    respond new Game(params)
  }

  def edit(Game game) {
    respond gameService.edit(game)
  }

  @Transactional
  def save(Game game) {
    updateRichText(game)
    if (validateData(game, 'create')) {
      gameService.save(game)
      respondChange("Новая игра ${game.title} успешно создана", CREATED,
          [action: 'play', params: [gameAlias: game.alias]])
    }
  }

  @Transactional
  def update(Game game) {
    if (!params.modules)
      game.modules.clear()

    updateRichText(game)
    if (validateData(game, 'edit')) {
      gameService.update(game)
      respondChange('Параметры игры изменены', OK,
          [action: 'play', params: [gameAlias: game.alias]])
    }
  }

  private void updateRichText(Game game) {
    game.overview = cleanHtml(game.overview, 'rich-text')
    game.preview = cleanHtml(game.preview, 'simple-rich-text')
    game.previewPureLength = cleanHtml(game.preview, 'none').length()
  }

  @Secured(['ROLE_ADMIN'])
  @Transactional
  def delete(Game game) {
    if (validateData(game)) {
      game.delete flush: true
      respondChange('Игра успешно удалена', NO_CONTENT)
    }
  }

  @Transactional
  def addMaster(Game game) {
    doAjax {
      def masterId = params.long("masterId")
      if (!masterId) throw new AjaxException("No master id")
      def master = SpringUser.get(masterId)
      if (!master) throw new AjaxException("Wrong master id")
      try {
        gameService.addMaster(game, master)
      } catch (RuntimeException e) {
        throw new AjaxException(e.getMessage(), e)
      }

      render template: 'masters', model: [masters: game.masters]
    }
  }

  @Transactional
  def removeMaster(Game game) {
    doAjax {
      def masterId = params.long("masterId")
      if (!masterId) throw new AjaxException("No master id")
      def master = SpringUser.get(masterId)
      if (!master) throw new AjaxException("Wrong master id")
      try {
        gameService.removeMaster(game, master)
      } catch (RuntimeException e) {
        throw new AjaxException(e.getMessage(), e)
      }

      render template: 'masters', model: [masters: game.masters]
    }
  }

}
