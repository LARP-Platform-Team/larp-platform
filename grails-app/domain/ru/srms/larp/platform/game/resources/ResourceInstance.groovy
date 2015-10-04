package ru.srms.larp.platform.game.resources

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.character.GameCharacter

/**
 * E.g. bank account, personal mana bar, etc.
 */
class ResourceInstance implements InGameStuff, Titled {

  String identifier
  ResourceOrigin origin
  Boolean transferable = false
  Boolean ownerEditable = false
  GameCharacter owner
  Double value = 0

  Double minValue
  Double maxValue

  static belongsTo = [type: GameResource]

  static constraints = {
    owner nullable: true
    origin nullable: true
    identifier maxSize: 64, unique: 'type'
    minValue nullable: true
    maxValue nullable: true
    value validator: { val, obj ->
      if(obj.minValue != null && val < obj.minValue)
        return 'resourceInstance.value.ltMinValue'
      if(obj.maxValue != null && val > obj.maxValue)
        return 'resourceInstance.value.gtMaxValue'
    }
  }

  public static def getAllByGame(Game game) {
    ResourceInstance.findAll("from ResourceInstance as res WHERE res.type.game=? ORDER BY res.type.title, res.identifier", [game])
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

  def beforeValidate() {
    this.value = Math.round(this.value * 100) / 100
  }

  String getFullId() {
    def result = this.identifier
    if(this.type.identifierTitle)
      result = "${this.type.identifierTitle}: ${result}"
    return result
  }

  @Override
  Game extractGame() {
    return type.game
  }

  @Override
  String extractTitle() {
    return "${type.storage} ($fullId)"
  }
}
