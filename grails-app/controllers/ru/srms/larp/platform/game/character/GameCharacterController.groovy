package ru.srms.larp.platform.game.character

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.CharacterService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameCharacterController extends BaseController {

    static allowedMethods = []
    CharacterService characterService

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

    @Override
    protected String labelCode() {
        return 'gameCharacter.label'
    }
}
