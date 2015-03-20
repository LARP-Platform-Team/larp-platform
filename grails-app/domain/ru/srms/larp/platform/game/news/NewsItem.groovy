package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.TitledIdentifiable

class NewsItem implements InGameStuff, TitledIdentifiable
{

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

    @Override
    Game getGame() {
        return feed.game
    }
}
