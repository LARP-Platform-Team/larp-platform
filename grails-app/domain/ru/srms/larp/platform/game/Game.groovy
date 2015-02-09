package ru.srms.larp.platform.game

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.ShiroUser
import ru.srms.larp.platform.sec.SpringUser

class Game {

    String title
    static hasMany = [masters: SpringUser, characters: GameCharacter]

    static constraints = {
    }

    static mapping = {
        masters joinTable: "game_masters"
    }
}
