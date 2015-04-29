package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled

class GameResource implements InGameStuff, Titled {

  String title
  String measure

  static hasMany = [instances: ResourceInstance, origins: ResourceOrigin]
  static belongsTo = [game: Game]

  static constraints = {
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
