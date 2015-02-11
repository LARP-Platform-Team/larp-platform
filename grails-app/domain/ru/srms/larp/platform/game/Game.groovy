package ru.srms.larp.platform.game

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

class Game {

    String title
    String alias
    String overview
    static hasMany = [masters: SpringUser, characters: GameCharacter]

    static constraints = {
        alias matches:/^[A-Za-z0-9\-]+$/
        overview maxSize: 9999
    }

    static mapping = {
        masters joinTable: "game_masters"
    }

    @Override
    String toString() {
        return title
    }
}
