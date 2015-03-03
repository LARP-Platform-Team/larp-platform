package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game

class NewsFeed {

    String title

    static belongsTo = [game: Game]
    static hasMany = [newsItems: NewsItem]

    static mapping = {
        newsItems sort: 'created', order: 'desc'
    }

    static constraints = {
        // TODO make it case-insensitive
        title validator: {val, obj ->
            NewsFeed.findByGameAndTitleIlikeAndIdNotEqual(obj.game, val, obj.id) == null}
    }
}
