package ru.srms.larp.platform.game.mail

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.character.GameCharacter

class MailBox implements InGameStuff, Titled {

  String address
  String name
  GameCharacter owner
  static belongsTo = [game: Game]
  static hasMany = [letters: LetterRef]

  static constraints = {
    address maxSize: 64, blank: false, email: true, unique: 'game'
    owner nullable: true
    name maxSize: 64, nullable: true, blank: true
    letters nullable: true
  }

  @Override
  String toString() {
    (name ? "$name " : '') + "<$address>"
  }

  @Override
  Game extractGame() {
    return game
  }

  @Override
  String extractTitle() {
    return address
  }
}
