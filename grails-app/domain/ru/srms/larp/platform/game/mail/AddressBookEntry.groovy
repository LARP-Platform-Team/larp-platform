package ru.srms.larp.platform.game.mail

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class AddressBookEntry implements InGameStuff {

  MailBox entry
  String entryAddress

  static belongsTo = [mailBox: MailBox]

  static constraints = {
    entry nullable: true, unique: 'mailBox'
  }

  def beforeValidate() {
    if(entry)
      entryAddress = entry.address
  }

  @Override
  Game extractGame() {
    return mailBox.game
  }
}
