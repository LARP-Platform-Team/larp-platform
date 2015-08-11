package larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.resources.ResourceInstance

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


  def breadCrumbs = {attrs, body ->
      out << g.link([uri: '/'], 'Главная')

      if(params.game) {
        out << " &rarr; "
        out << g.link([mapping: 'game', params: [gameAlias: params.gameAlias]],
            (params.game as Titled).extractTitle())
      }

    if(params.character) {
      out << " &rarr; "
      out << g.link([
          mapping: 'playAs',
          params: [gameAlias: params.gameAlias, charAlias: params.charAlias]],
          (params.character as Titled).extractTitle())
    }
  }



}
