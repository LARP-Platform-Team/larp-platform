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
  String identifierTitle = "Номер счета"

  static hasMany = [instances: ResourceInstance, origins: ResourceOrigin]
  static belongsTo = [game: Game]

  static constraints = {
    measure maxSize: 8, nullable: true
    identifierTitle maxSize: 64
    // TODO replace this with unique:['property'] index everywhere!
    title maxSize: 64, validator: {val, obj ->
      GameResource.findByGameAndTitleIlikeAndIdNotEqual(obj.game, val, obj.id) == null}
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
