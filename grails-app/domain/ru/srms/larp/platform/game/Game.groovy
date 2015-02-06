package ru.srms.larp.platform.game

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.ShiroUser

class Game {

    String title
    static hasMany = [masters: ShiroUser, characters: GameCharacter]

    static constraints = {
    }

    static mapping = {
        masters joinTable: "game_masters"
    }
}
