package ru.srms.larp.platform.game.resources

import org.apache.commons.lang.StringUtils
import org.quartz.TriggerKey
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class ResourcePeriodicRule implements InGameStuff {

  Double value
  ResourceInstance source
  String sourceName
  String comment

  Integer fireHour = 8
  Integer fireMinute = 0

  static belongsTo = [target: ResourceInstance]
  static hasMany = [fireDays: WeekDays]

  static constraints = {
    source nullable: true, validator: {val, obj -> val == null || obj.target.id != val.id}
    comment nullable: true, maxSize: 128
    sourceName validator: {val, obj -> StringUtils.isNotEmpty(val) || obj.source != null }
    value notEqual: 0d
    fireDays validator: {val, obj -> val && val.size() > 0}
    fireHour range: 0..23
    fireMinute range: 0..59
  }

  @Override
  Game extractGame() {
    return target.extractGame()
  }

  def getTriggerKey() {
    TriggerKey.triggerKey("Rule_${this.id}", PeriodicResourceUpdateJob.PERIODIC_UPDATE_JOB_KEY)
  }

  def getFormattedTime() {
    String.format("%02d", fireHour) + ":" + String.format("%02d", fireMinute)
  }

  def beforeValidate() {
    this.value = Math.round(this.value * 100) / 100
  }


  static enum WeekDays {
    // TODO find some constants
    MON('Пн', 2),
    TUE('Вт', 3),
    WED('Ср', 4),
    THU('Чт', 5),
    FRI('Пт', 6),
    SAT('Сб', 7),
    SUN('Вс', 1);

    String title
    int code

    WeekDays(String title, int code) {
      this.title = title
      this.code = code
    }
  }
}
