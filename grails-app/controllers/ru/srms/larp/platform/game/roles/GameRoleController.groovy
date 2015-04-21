package ru.srms.larp.platform.game.roles

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.GameAclService
import ru.srms.larp.platform.GameRoleService
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.permissions.GamePermission

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameRoleController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", addToChar: "POST"]

    GameRoleService gameRoleService
    NewsService newsService
    GameAclService gameAclService

    def index() {
        respond gameRoleService.list(params.game, null, paginator()) ,
                model:[rolesCount: gameRoleService.count(params.game, null)]
    }

    def create() {
        respond gameRoleService.create(params.game,
                params.parent ? GameRole.get(params.parent) : null)
    }

    def edit(GameRole role) {
        respond gameRoleService.edit(role)
    }

    @Transactional
    def save(GameRole role) {
       if(validateData(role, 'create')) {
            gameRoleService.save(role)
            respondChange('default.created.message', CREATED, role)
        }
    }

    @Transactional
    def update(GameRole role) {
        if(validateData(role, 'edit')) {
            gameRoleService.save(role)
            respondChange('default.updated.message', OK, role)
        }
    }

    @Transactional
    def delete(GameRole role) {
        if(validateData(role)) {
            gameRoleService.delete(role)
            respondChange('default.deleted.message', NO_CONTENT, null, role.id)
        }
    }

    def config(GameRole role) {
        render(view: "config", model: [
                acls: gameAclService.getAclMatrix(role)
        ])
    }

    @Transactional
    def setPermission() {
        try {
            def role = GameRole.get(params.id)
            if (!role) throw new Exception("Неверная роль")
            def permission = GamePermission.valueOf(params.permission)
            def clazz = this.class.classLoader.loadClass(params.clazz)
            boolean value = gameAclService.setPermission(role, clazz, params.long("itemId"), permission)

            render value
        } catch (Exception e) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render e.class.simpleName + ": " + e.message
        }
    }

    @Transactional
    def addToChar(GameRole gameRole) {

        Long id = params.character?.id as Long
        if(!id || !GameCharacter.exists(id)) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render "Указан неверный персонаж!"
            return
        }

        if(gameRole.characters.find { it -> it.id == id }) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render "Указанный персонаж уже назначен на эту роль!"
            return
        }

        try {
            gameRoleService.add(gameRole, GameCharacter.get(id))
        } catch (all) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render "Что-то пошло не так: $all.message"
            return
        }

        render template: 'characters', model: [characters: gameRole.characters]
    }

    // TODO update with doAjax from BaseController
    @Transactional
    def removeFromChar(GameRole gameRole) {
        Long id = params.characterId as Long
        if(!id) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render "Указан неверный персонаж!"
            return
        }

        try {
            gameRoleService.remove(gameRole, GameCharacter.get(id))
        } catch (all) {
            response.status = INTERNAL_SERVER_ERROR.value()
            render "Что-то пошло не так: $all.message"
            return
        }

        render template: 'characters', model: [characters: gameRole.characters]
    }

    @Override
    protected String labelCode() { 'gameRole.label' }

}
