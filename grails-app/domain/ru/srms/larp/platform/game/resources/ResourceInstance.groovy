package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.character.GameCharacter

/**
 * E.g. bank account, personal mana bar, etc.
 */
class ResourceInstance implements InGameStuff, Titled {

  String title
  String identifier
  ResourceOrigin origin
  Boolean transferable = false
  Boolean ownerEditable = false
  GameCharacter owner
  Double value = 0

  static belongsTo = [type: GameResource]

  static constraints = {
    owner nullable: true
    origin nullable: true
    title maxSize: 64, unique: 'type'
    identifier maxSize: 64, unique: 'type'
  }

  public static def getAllByGame(Game game) {
    ResourceInstance.findAll("from ResourceInstance as res WHERE res.type.game=? ORDER BY res.type.title, res.title", [game])
  }

  public def getTransferLogs() {
    TransferLogEntry.findAllBySourceOrTarget(this, this);
  }

  public def getPeriodicRules() {
    ResourcePeriodicRule.findAllByTarget(this)
  }

  public static def groupByType(List<ResourceInstance> list) {
    Map<GameResource, List<ResourceInstance>> result = [:]
    list.each {
      result.putIfAbsent(it.type, [])
      result.get(it.type).add(it)
    }
    result
  }

  String getFullId() {
    def result = this.identifier
    if(this.type.identifierTitle)
      result = "${this.type.identifierTitle}${result}"
    return result
  }

  @Override
  Game extractGame() {
    return type.game
  }

  @Override
  String extractTitle() {
    return type.title+ ": " + title
  }
}
