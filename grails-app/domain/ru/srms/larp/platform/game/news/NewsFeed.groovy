package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game

class NewsFeed {

    String title

    static belongsTo = [game: Game]
    static hasMany = [newsItems: NewsItem]

    static constraints = {
    }
}
