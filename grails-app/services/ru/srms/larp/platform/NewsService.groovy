package ru.srms.larp.platform

import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.news.NewsItem

@Transactional(readOnly = true)
class NewsService {

    GameAclService gameAclService
    AclUtilService aclUtilService

    @PostFilter("hasPermission(#game, admin) or hasPermission(filterObject, read)")
    def findFeedsByGame(Game game) {
        NewsFeed.findAllByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def listAdminFeeds(Game game, Map pagination = null) {
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
        boolean insert = feed.id == null
        feed.save()
        if(insert) gameAclService.createAcl(feed)
    }

    @Transactional
    @PreAuthorize("hasPermission(#feed.game, admin)")
    def deleteFeed(NewsFeed feed) {
        aclUtilService.deleteAcl(feed)
        feed.delete flush:true
    }

    @PreAuthorize("hasPermission(#feed.game, admin)")
    def editFeed(NewsFeed feed) { feed }

    @PreAuthorize("hasPermission(#feed.game, admin) or hasPermission(#feed, read)")
    def readFeed(NewsFeed feed) { feed }

    @PreAuthorize("hasPermission(#parent.game, admin) or \
            (hasPermission(#parent, create) and #parent.game.active)")
    NewsItem createNews(NewsFeed parent) {
        new NewsItem(feed: parent)
    }

    @Transactional
    @PreAuthorize("hasPermission(#news.feed.game, admin) or \
            (hasPermission(#news.feed, create) and #news.feed.game.active)")
    def saveNews(NewsItem news) {
        news.save flush:true
    }

    @PreAuthorize("hasPermission(#news.feed.game, admin) or \
            (hasPermission(#news.feed, write) and #news.feed.game.active)")
    def editNews(NewsItem news) {news}

    @Transactional
    @PreAuthorize("hasPermission(#news.feed.game, admin) or \
            (hasPermission(#news.feed, write) and #news.feed.game.active)")
    def updateNews(NewsItem news) {
        news.save flush:true
    }

    @Transactional
    @PreAuthorize("hasPermission(#news.feed.game, admin) or \
            (hasPermission(#news.feed, delete) and #news.feed.game.active)")
    def deleteNews(NewsItem news) {
        news.delete flush:true
    }
}
