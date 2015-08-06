package ru.srms.larp.platform.game.resources

import grails.validation.Validateable

@Validateable
class TransferData {
  String transferTargetId
  String comment
  Double transferValue
  ResourceInstance source

  static constraints = {
    comment nullable: true, maxSize: 128
    transferValue min: 0d, notEqual: 0d,
        validator: {val, obj -> obj.source && val <= obj.source.value}
    transferTargetId blank: false,
        validator: {val, obj -> obj.source && ResourceInstance.countByIdentifierAndType(val, obj.source.type) == 1}
  }
}
