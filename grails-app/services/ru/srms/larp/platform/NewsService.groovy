package ru.srms.larp.platform

import grails.transaction.Transactional
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.news.NewsFeed

@Transactional
class NewsService {

    def findFeedsByGame(Game game) {
        NewsFeed.findAllByGame(game)
    }
}
