package ru.srms.larp.platform.game.character

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.sec.ShiroUser

class GameCharacter {

    String name

    static belongsTo = [player: ShiroUser, game: Game]

    static constraints = {
        name maxSize: 64
        player nullable: true
    }

    @Override
    String toString() {
        name
    }
}
