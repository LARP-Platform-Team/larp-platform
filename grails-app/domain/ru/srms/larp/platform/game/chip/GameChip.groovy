package ru.srms.larp.platform.game.chip

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

class GameChip {

  String code
  String title
  String notes
  GameCharacter owner

  static belongsTo = [game: Game]

  static constraints = {
    code maxSize: 32, unique: 'game'
    title maxSize: 128
    notes maxSize: 1024, nullable: true
    owner nullable: true
  }
}
