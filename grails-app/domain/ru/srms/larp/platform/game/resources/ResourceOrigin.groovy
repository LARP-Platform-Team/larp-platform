package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled

class ResourceOrigin implements InGameStuff, Titled {

  String title
  static belongsTo = [resource: GameResource]

  static constraints = {
  }

  @Override
  Game extractGame() {
    return resource.game
  }

  @Override
  String extractTitle() {
    return title
  }
}
