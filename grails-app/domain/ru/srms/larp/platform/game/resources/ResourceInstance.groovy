package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.character.GameCharacter

class ResourceInstance {

  String title
  ResourceOrigin origin
  Boolean transferable
  Boolean ownerEditable
  GameCharacter owner

  static belongsTo = [resource: GameResource]

  static constraints = {
    origin nullable: true
  }
}
