package larp.platform

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

class GameFilters {

    def filters = {

        game(uri: '/play/**') {
            before = {
                params.game = Game.findByAlias(params.gameAlias)
                // TODO temp
                print 'game filter: ' + params.game
            }
        }

        character(uri: '/play/*/as/**') {
            before = {
                params.character = GameCharacter.findByAlias(params.charAlias)
                // TODO temp
                print 'character filter: ' + params.character
            }
        }
    }
}
