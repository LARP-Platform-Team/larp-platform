package larp.platform

import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.resources.ResourceInstance

class SnippetsTagLib {
//  static defaultEncodeAs = [taglib: 'html']
  //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

  NewsService newsService
  MailboxService mailboxService
  ResourceService resourceService

  def availableMailboxes = { attrs, body ->
    out << render(template: '/mailBox/available', model: [
        items: mailboxService.available(attrs.game ?: params.game)])
  }

  def newsFeeds = {attrs, body ->
    out << render(template: '/newsFeed/feedsList', model: [
        feeds: newsService.findFeedsByGame(attrs.game ?: params.game)])
  }

  def availableResources = {attrs, body ->
    out << render(template: '/resourceInstance/available', model: [
        items: ResourceInstance.groupByType(
            resourceService.findInstancesByGame(attrs.game ?: params.game))])
  }
}
