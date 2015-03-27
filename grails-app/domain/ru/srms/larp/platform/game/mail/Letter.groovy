package ru.srms.larp.platform.game.mail

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.character.GameCharacter

class Letter implements InGameStuff {

    String subject;
    String text;

    static belongsTo = [sender: GameCharacter]
    static hasMany = [recepients: GameCharacter]

    static constraints = {
        text maxSize: 9999
        // all related characters must belong to the same game
        sender validator: {val, obj -> obj.recepients.any {val.game != it.game} }
    }

    @Override
    Game extractGame() {
        return sender.game
    }
}
