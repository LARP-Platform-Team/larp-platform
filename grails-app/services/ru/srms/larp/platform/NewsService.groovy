package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.news.NewsFeed

@Transactional(readOnly = true)
class NewsService {

    // TODO post filter
    def findFeedsByGame(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def listAdminFeeds(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    NewsFeed createFeed(Game game) {
        new NewsFeed(game: game)
    }

    @Transactional
    @PreAuthorize("hasPermission(#feed.game, admin)")
    def saveFeed(NewsFeed feed) {
        feed.save flush:true
    }

    @Transactional
    @PreAuthorize("hasPermission(#feed.game, admin)")
    def deleteFeed(NewsFeed feed) {
        feed.delete flush:true
    }

    @PreAuthorize("hasPermission(#feed.game, admin)")
    def editFeed(NewsFeed feed) { feed }

    // TODO check permissions
    def readFeed(NewsFeed feed) { feed }
}
