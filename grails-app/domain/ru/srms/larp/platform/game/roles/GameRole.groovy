package ru.srms.larp.platform.game.roles

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class GameRole  implements InGameStuff {

    String title

    static belongsTo = [game: Game, parent: GameRole]
    static hasMany = [subRoles: GameRole]

    static constraints = {
        parent nullable: true, validator: {val, obj -> val == null || val.game == obj.game}
    }

    @Override
    Game extractGame() {
        return game
    }
}
