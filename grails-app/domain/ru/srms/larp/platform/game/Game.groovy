package ru.srms.larp.platform.game

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

class Game {

    String title
    String alias
    String overview
    // TODO is characters relation side needed here?
    static hasMany = [masters: SpringUser, characters: GameCharacter]

    static constraints = {
        title unique: true
        alias matches:/^[A-Za-z0-9\-]+$/, unique: true
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
