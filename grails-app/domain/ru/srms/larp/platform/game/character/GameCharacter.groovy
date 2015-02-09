package ru.srms.larp.platform.game.character

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.sec.ShiroUser
import ru.srms.larp.platform.sec.SpringUser

class GameCharacter {

    String name

    static belongsTo = [player: SpringUser, game: Game]

    static constraints = {
        name maxSize: 64
        player nullable: true
    }

    @Override
    String toString() {
        name
    }
}
