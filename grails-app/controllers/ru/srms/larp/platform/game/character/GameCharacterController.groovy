package ru.srms.larp.platform.game.character

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.CharacterService

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameCharacterController {

    static allowedMethods = []
    CharacterService characterService

    def playAs(String charAlias) {
        def character = characterService.find(charAlias)
        if(character == null) {
            flash.message = message(code: 'default.not.found.message', args: ['Character', charAlias])
            redirect(controller: 'game', action: 'play', params: [alias: params.gameAlias])
            return
        }

        respond character, view: 'show'
    }

}
