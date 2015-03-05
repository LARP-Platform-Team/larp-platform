package ru.srms.larp.platform.game.character

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.SpringUser

class GameCharacter implements InGameStuff {

    String name
    String alias
    Boolean isDead = false

    static belongsTo = [game: Game]
    static hasOne = [player: SpringUser]
    static hasMany = [roles: GameRole]

    static mapping = {
        roles cascade: "save-update"
    }

    static constraints = {
        // character names and aliases must be unique in the game context
        name maxSize: 64, validator: {val, obj ->
            GameCharacter.findByGameAndNameIlikeAndIdNotEqual(obj.game, val, obj.id) == null}
        alias matches:/^[A-Za-z0-9\-]+$/, validator: {val, obj ->
            GameCharacter.findByGameAndAliasIlikeAndIdNotEqual(obj.game, val, obj.id) == null}
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
