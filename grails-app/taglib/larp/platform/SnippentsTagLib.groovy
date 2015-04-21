package larp.platform

import ru.srms.larp.platform.MailboxService

class SnippentsTagLib {
//  static defaultEncodeAs = [taglib: 'html']
  //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

  MailboxService mailboxService

  def availableMailboxes = { attrs, body ->
    out << render(template: '/mailBox/available', model: [
        items: mailboxService.available(attrs.game ?: params.game)])
  }
}
