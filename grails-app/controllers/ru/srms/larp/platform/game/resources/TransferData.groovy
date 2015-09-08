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
        validator: { val, obj ->
          // проверим, что на ресурсе-источнике значение не упадет ниже минимума
          if (obj.source) {
            if (obj.source.minValue != null && obj.source.value - val < obj.source.minValue)
              return 'transferData.transferValue.notEnough'

            // а на ресурсе-получателе не поднимется выше максимума
            if (obj.transferTargetId) {
              def target = ResourceInstance.findByIdentifierAndType(obj.transferTargetId, obj.source.type)
              if (target && target.maxValue != null && target.value + val > target.maxValue) {
                return 'transferData.transferValue.targetOverfilled'
              }
            }
          }
        }
    transferTargetId blank: false,
        validator: { val, obj -> obj.source && ResourceInstance.countByIdentifierAndType(val, obj.source.type) == 1 }
  }

  def beforeValidate() {
    this.transferValue = Math.round(this.transferValue * 100) / 100
  }
}
