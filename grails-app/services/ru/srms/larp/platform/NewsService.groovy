package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.news.NewsFeed

@Transactional
class NewsService {

    def findFeedsByGame(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def listAdminFeeds(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def createFeed(Game game) {
        new NewsFeed(game: game)
    }

    @PreAuthorize("hasPermission(#feed.game, admin)")
    def saveFeed(NewsFeed feed) {
        feed.save flush:true
    }
}
