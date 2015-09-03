package ru.srms.larp.platform.game.mail

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class LetterContent implements InGameStuff {

  String subject
  String text
  Date time
  String letterFrom
  String letterTo
  MailBox sender
  static hasMany = [recipients: MailBox]

  static constraints = {
    subject maxSize: 64
    text maxSize: 9999
    // all related characters must belong to the same game
//        sender validator: {val, obj -> !obj.recipients || obj.recipients.any {val.game != it.game} }
  }

  def beforeValidate() {
    letterFrom = sender.toString()
    letterTo = recipients.collect { it.toString() }.join("; ")
  }

  @Override
  Game extractGame() {
    return sender.game
  }
}
