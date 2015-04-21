package larp.platform

import ru.srms.larp.platform.NewsService

// TODO add to snippets tag lib
class NewsTagLib {
    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    NewsService newsService

    def newsFeeds = {attrs, body ->
        out << render(template: '/newsFeed/feedsList', model: [
                feeds: newsService.findFeedsByGame(attrs.game ?: params.game)])
    }
}
