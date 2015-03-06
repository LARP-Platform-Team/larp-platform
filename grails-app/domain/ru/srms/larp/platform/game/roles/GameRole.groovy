package ru.srms.larp.platform.game.roles

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.character.GameCharacter

class GameRole  implements InGameStuff {

    String title
    Game game
    GameRole parent

    static belongsTo = [Game, GameRole, GameCharacter]
    static hasMany = [subRoles: GameRole, characters: GameCharacter]

    // TODO subRoles are lazy to avoid N+1 problem. figure it out.
    static mapping = {
        subRoles sort: 'title', order: 'asc', lazy: false
        sort title: "asc"
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
