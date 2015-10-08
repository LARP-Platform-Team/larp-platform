package larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.resources.ResourceInstance

// TODO all this "tag" may be implemented by adding info to the model
class SnippetsTagLib {
//  static defaultEncodeAs = [taglib: 'html']
  //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

  NewsService newsService
  MailboxService mailboxService
  ResourceService resourceService
  SpringSecurityService springSecurityService

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
