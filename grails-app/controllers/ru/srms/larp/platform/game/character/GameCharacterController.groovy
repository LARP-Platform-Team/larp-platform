package ru.srms.larp.platform.game.character

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.CharacterService
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.game.Game

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameCharacterController {

    static allowedMethods = []
    CharacterService characterService
    NewsService newsService

    def playAs(String charAlias) {
        def character = characterService.find(charAlias)
        if(character == null) {
            flash.message = message(code: 'default.not.found.message', args: ['Character', charAlias])
            redirect(controller: 'game', action: 'play', params: [alias: params.gameAlias])
            return
        }

        render(view: 'cabinet', model: [
                character: character,
                newsFeeds: newsService.findFeedsByGame(Game.findByAlias(params.gameAlias))])
    }

}
