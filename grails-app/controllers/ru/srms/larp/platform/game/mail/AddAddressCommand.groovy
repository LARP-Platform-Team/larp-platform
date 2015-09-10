package ru.srms.larp.platform.game.mail

import grails.validation.Validateable
import ru.srms.larp.platform.game.Game

/**
 *
 * <p>Created 10.09.15</p>
 */
@Validateable
class AddAddressCommand {
  String newAddress
  Game game
  MailBox currentBox

  static constraints = {
    newAddress nullable: false, blank: false, validator: { val, obj ->
      def box = MailBox.findByGameAndAddress(obj.game, obj.newAddress)
      if(!box)
        return 'not.found'
      if(box == obj.currentBox)
        return 'same.box'
      if(obj.currentBox.savedAddresses.any {it.entry == box || it.entryAddress == box.address})
        return 'already.in.book'
    }
  }
}
