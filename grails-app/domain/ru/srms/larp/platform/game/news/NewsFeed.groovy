package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.TitledIdentifiable

class NewsFeed implements InGameStuff, TitledIdentifiable {

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
