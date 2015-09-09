package ru.srms.larp.platform.game.mail

import org.apache.commons.lang.StringUtils
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class LetterContent implements InGameStuff {

  String subject
  String text
  Date time
  String letterFrom
  String letterTo
  MailBox sender
  transient String targetAddresses
  transient Integer inputTargetsQty
  transient Boolean updateRecipients = false
  static hasMany = [recipients: MailBox]

  static transients = ['targetAddresses', 'inputTargetsQty', 'updateRecipients']

  static constraints = {
    subject maxSize: 64
    text maxSize: 9999
    recipients validator: { val, obj ->
      if (!val)
        return 'no.recipients'
      // all recipients must belong to the same game
      if(val.any {it.game != obj.extractGame()})
        return 'wrong.game'
    }
    targetAddresses nullable: true, bindable: true, validator: { val, obj ->
      if(obj.inputTargetsQty != null && obj.inputTargetsQty != obj.recipients.size())
        return 'incorrect.recipients'
    }
  }

  def beforeValidate() {
    if(updateRecipients) convertAddresses()
    letterFrom = sender.toString()
    letterTo = recipients.collect { it.toString() }.join("; ")
  }

  private def convertAddresses() {
    if(!targetAddresses ) {
      inputTargetsQty = 0
      setRecipients(new HashSet<>())
      return
    }

    def addresses = targetAddresses
        .tokenize(',')
        .collect { StringUtils.trimToEmpty(it) }
        .findAll { StringUtils.isNotEmpty(it) }
        .unique()
    inputTargetsQty = addresses.size()
    addresses = addresses
        .collect { MailBox.findByGameAndAddress(this.extractGame(), it) }
        .findAll { it != null}
    setRecipients(new HashSet<>(addresses))
  }

  @Override
  Game extractGame() {
    return sender.game
  }
}
