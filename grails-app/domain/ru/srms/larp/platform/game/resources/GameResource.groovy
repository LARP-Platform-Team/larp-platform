package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled

/**
 * E.g. gold, mana, etc.
 */
class GameResource implements InGameStuff, Titled {

  String title
  String measure = ""
  String storage
  String identifierTitle = "Номер счета"
  Double minValue
  Double maxValue

  static hasMany = [instances: ResourceInstance, origins: ResourceOrigin]
  static belongsTo = [game: Game]

  static constraints = {
    measure maxSize: 16, nullable: true
    identifierTitle maxSize: 64
    storage maxSize: 64
    title maxSize: 64, unique: 'game'
    minValue nullable: true
    maxValue nullable: true
  }

  @Override
  Game extractGame() {
    return game
  }

  @Override
  String extractTitle() {
    return title
  }
}
