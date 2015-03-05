package ru.srms.larp.platform.game.roles

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.character.GameCharacter

class GameRole  implements InGameStuff {

    String title
    Game game
    GameRole parent

    // TODO think is it good to have many-to-many bidirectional mapping
    static belongsTo = [Game, GameRole, GameCharacter]
    static hasMany = [subRoles: GameRole, characters: GameCharacter]

    static mapping = {
        subRoles sort: 'title', order: 'asc'
        sort title: "asc"
        characters cascade: "save-update"
    }

    static constraints = {
        parent nullable: true, validator: {val, obj -> val == null || val.game == obj.game}
        characters nullable: true
    }

    @Override
    Game extractGame() {
        return game
    }

    @Override
    public String toString() {
        return title;
    }
}
