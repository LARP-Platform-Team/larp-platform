package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled

/**
 * E.g. bank name, radiation source, etc.
 */
class ResourceOrigin implements InGameStuff, Titled {

  String title
  static belongsTo = [resource: GameResource]

  static constraints = {
    title maxSize: 64, unique: ['resource']
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
