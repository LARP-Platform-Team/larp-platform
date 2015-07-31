package ru.srms.larp.platform.game.resources

class TransferLogEntry {

  Double value
  Date date = new Date()
  ResourceInstance source
  ResourceInstance target
  String sourceName
  String targetName
  String comment

  static mapping = {
    sort date: 'desc'
  }

  static constraints = {
    source nullable: true
    target nullable: true
    comment nullable: true, maxSize: 128
    value min: 0d, notEqual: 0d
  }
}
