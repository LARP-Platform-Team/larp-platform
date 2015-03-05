package ru.srms.larp.platform.game.roles

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.GameRoleService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameRoleController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST"]

    GameRoleService gameRoleService

    def index() {
        respond gameRoleService.list(params.game, null, paginator()) ,
                model:[rolesCount: gameRoleService.count(params.game, null)]
    }

    def create() {
        respond gameRoleService.create(params.game,
                params.parent ? GameRole.get(params.parent) : null)
    }

    def show(GameRole gameRoleInstance) {
        respond gameRoleInstance
    }

    @Transactional
    def save(GameRole gameRoleInstance) {
       if(validateData(gameRoleInstance, 'create')) {
            gameRoleInstance.save flush: true
            respondChange('default.created.message', CREATED, gameRoleInstance)
        }
    }

    def edit(GameRole gameRoleInstance) {
        respond gameRoleInstance
    }

    @Transactional
    def update(GameRole gameRoleInstance) {
        if(validateData(gameRoleInstance, 'edit')) {
            gameRoleInstance.save flush: true
            respondChange('default.updated.message', OK, gameRoleInstance)
        }
    }

    @Transactional
    def delete(GameRole gameRoleInstance) {

        if(validateData(gameRoleInstance)) {
            gameRoleInstance.delete flush:true
            respondChange('default.deleted.message', NO_CONTENT, null, gameRoleInstance.id)
        }
    }

    @Override
    protected String labelCode() { 'gameRole.label' }

}
