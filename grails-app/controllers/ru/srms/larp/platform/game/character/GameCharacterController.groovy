package ru.srms.larp.platform.game.character

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.CharacterService
import ru.srms.larp.platform.GameRoleService
import ru.srms.larp.platform.game.roles.GameRole

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameCharacterController extends BaseController {

    static allowedMethods = []
    CharacterService characterService
    GameRoleService gameRoleService

    def playAs(String charAlias) {
        def character = characterService.find(charAlias)
        if(character == null) {
            flash.message = message(code: 'default.not.found.message', args: ['Character', charAlias])
            redirect(controller: 'game', action: 'play', params: [alias: params.gameAlias])
            return
        }

        render(view: 'cabinet', model: [character: character])
    }

    def index() {
        render(view: 'index', model: [
                characters: characterService.list(params.game, paginator()),
                itemsCount: characterService.count(params.game)])
    }

    def create() {
        respond characterService.create(params.game)
    }

    def edit(GameCharacter character) {
        respond characterService.edit(character)
    }

    @Transactional
    def save(GameCharacter character) {
        if(validateData(character, 'create')) {
            characterService.save(character)
            respondChange('default.created.message', CREATED, character)
        }
    }

    @Transactional
    def update() {
        def character = GameCharacter.get(params.id)
        def oldPlayer = character.player
        character.properties = params
        if(validateData(character, 'edit')) {
            characterService.save(character, oldPlayer)
            respondChange('default.updated.message', OK, character)
        }
    }

    @Transactional
    def delete(GameCharacter character) {
        if(validateData(character)) {
            characterService.delete(character)
            respondChange('default.deleted.message', NO_CONTENT, null, character.id)
        }
    }

    @Transactional
    def addRole(GameCharacter character) {
        doAjax {
            Long id = params.role?.id as Long
            if (!id || !GameRole.exists(id))
                throw new Exception("Указана неверная роль!")

            if(character.roles.find({it.id == id}))
                throw new Exception("У персонажа уже есть указанная роль!")

            gameRoleService.add(GameRole.get(id), character)
            render template: 'roles', model: [roles: character.roles]
        }
    }

    @Transactional
    def removeRole(GameCharacter character) {
        doAjax {
            Long id = params.roleId as Long
            if (!id || !GameRole.exists(id))
                throw new Exception("Указана неверная роль!")

            gameRoleService.remove(GameRole.get(id), character)
            render template: 'roles', model: [roles: character.roles]
        }
    }

    @Override
    protected String labelCode() {
        return 'gameCharacter.label'
    }
}
