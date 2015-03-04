package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class NewsItem implements InGameStuff
        //implements Comparable
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

//        @Override
//    int compareTo(Object o) {
//        created.compareTo(obj.created)
//    }
    @Override
    Game extractGame() {
        return feed.game
    }
}
