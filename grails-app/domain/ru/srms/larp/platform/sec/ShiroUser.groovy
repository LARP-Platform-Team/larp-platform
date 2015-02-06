package ru.srms.larp.platform.sec

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

class ShiroUser {
    String username
    String passwordHash
    
    static hasMany = [ roles: ShiroRole, permissions: String, characters: GameCharacter]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }

    @Override
    String toString() {
        username
    }
}
