package ru.srms.larp.platform.game.character

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.sec.SpringUser

class GameCharacter implements InGameStuff {

    String name
    Boolean isDead = false

    static belongsTo = [player: SpringUser, game: Game]

    static constraints = {
        // character names must be unique in the game context
        name maxSize: 64, validator: {val, obj -> GameCharacter.findByGameAndName(obj.game, val) == null}
        player nullable: true
    }

    @Override
    String toString() {
        name
    }

    @Override
    Game extractGame() {
        return game
    }
}
