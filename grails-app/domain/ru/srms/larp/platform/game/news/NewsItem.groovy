package ru.srms.larp.platform.game.news

class NewsItem {

    String title
    String text
    Date created = new Date()

    static belongsTo = [feed: NewsFeed]

    static mapping = {
        sort created: 'desc'
    }

    static constraints = {
        text maxSize: 9999
    }
}
