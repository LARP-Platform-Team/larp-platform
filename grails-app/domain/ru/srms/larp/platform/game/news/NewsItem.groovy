package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled

class NewsItem implements InGameStuff, Titled
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
    Game extractGame() {
        return feed.game
    }

    @Override
    String extractTitle() {
        return title
    }
}
