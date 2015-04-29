package ru.srms.larp.platform.game.resources

class TransferLogEntry {

  Date date
  ResourceInstance source
  ResourceInstance target
  String sourceName
  String targetName
  String comment
  Long value

  static constraints = {
    source nullable: true
    target nullable: true
  }
}
