package ru.srms.larp.platform.game

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.GameService
import ru.srms.larp.platform.sec.SpringUser

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST"]
    GameService gameService

    @Secured(['permitAll'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Game.list(params), model:[gameInstanceCount: Game.count()]
    }

    @Secured(['permitAll'])
    def play() {
        respond params.game,
                model: [characters: gameService.getAvailableCharacters(params.game)]
    }

    def create() {
        respond new Game(params)
    }

    def edit(Long id) {
        respond gameService.edit(id)
    }

    @Transactional
    def save(Game game) {
        if(validateData(game, 'create')) {
            gameService.save(game)
            respondChange('default.created.message', CREATED, game)
        }
    }

    @Transactional
    def update(Game game) {
        if(validateData(game, 'create')) {
            gameService.update(game)
            respondChange('default.updated.message', OK, game)
        }
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(Game game) {
        if(validateData(game)) {
            game.delete flush: true
            respondChange('default.deleted.message', NO_CONTENT, null, game.id)
        }
    }

    @Transactional
    def addMaster(Game game) {
        doAjax {
            def masterId = params.long("masterId")
            if(!masterId) throw new Exception("No master id")
            def master = SpringUser.get(masterId)
            if(!master) throw new Exception("Wrong master id")
            gameService.addMaster(game, master)

            render template: 'masters', model: [masters: game.masters]
        }
    }

    @Transactional
    def removeMaster(Game game) {
        doAjax {
            def masterId = params.long("masterId")
            if(!masterId) throw new Exception("No master id")
            def master = SpringUser.get(masterId)
            if(!master) throw new Exception("Wrong master id")
            gameService.removeMaster(game, master)

            render template: 'masters', model: [masters: game.masters]
        }
    }

    @Override
    protected String labelCode() { 'game.label' }
}
