package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.news.NewsItem

@Transactional(readOnly = true)
class NewsService {

    // TODO post filter
    def findFeedsByGame(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def listAdminFeeds(Game game, Map pagination) {
        NewsFeed.findAllByGame(game, pagination)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def countAdminFeeds(Game game) {
        NewsFeed.countByGame(game)
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

    // TODO check permissions
    NewsItem createNews(NewsFeed parent) {
        new NewsItem(feed: parent)
    }

    // TODO check permissions
    def editNews(NewsItem news) {news}

    @Transactional
    // TODO check permissions
    def saveNews(NewsItem news) {
        news.save flush:true
    }

    @Transactional
    // TODO check permissions
    def deleteNews(NewsItem news) {
        print 'do delete!'
        news.delete flush:true
    }
}
