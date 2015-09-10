package ru.srms.larp.platform.game.character

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.CharacterService
import ru.srms.larp.platform.GameRoleService
import ru.srms.larp.platform.exceptions.AjaxException
import ru.srms.larp.platform.game.roles.GameRole

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameCharacterController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", addRole: "POST", removeRole: "POST"]
    CharacterService characterService
    GameRoleService gameRoleService

    def playAs(String charAlias) {
        def character = characterService.find(charAlias)
        if(character == null) {
            flash.warning = "Персонаж не найден."
            redirect(controller: 'game', action: 'play', params: [gameAlias: params.gameAlias])
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
            respondChange('Персонаж успешно добавлен', CREATED)
        }
    }

    @Transactional
    def update() {
        def character = GameCharacter.get(params.id)
        def oldPlayer = character.player
        character.properties = params
        if(validateData(character, 'edit')) {
            characterService.save(character, oldPlayer)
            respondChange('Персонаж обновлен', OK)
        }
    }

    @Transactional
    def delete(GameCharacter character) {
        if(validateData(character)) {
            characterService.delete(character)
            respondChange('Персонаж удален', NO_CONTENT)
        }
    }

    @Transactional
    def addRole(GameCharacter character) {
        doAjax {
            Long id = params.role?.id as Long
            if (!id || !GameRole.exists(id))
                throw new AjaxException("Указана неверная роль!")

            if(character.roles.find({it.id == id}))
                throw new AjaxException("У персонажа уже есть указанная роль!")

            gameRoleService.add(GameRole.get(id), character)
            render template: 'roles', model: [items: character.roles]
        }
    }

    @Transactional
    def removeRole(GameCharacter character) {
        doAjax {
            Long id = params.roleId as Long
            if (!id || !GameRole.exists(id))
                throw new AjaxException("Указана неверная роль!")

            gameRoleService.remove(GameRole.get(id), character)
            render template: 'roles', model: [items: character.roles]
        }
    }

}
