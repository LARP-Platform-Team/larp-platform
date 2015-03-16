package ru.srms.larp.platform.game.news

import ru.srms.larp.platform.AclControlled
import ru.srms.larp.platform.GameAclService
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

import javax.persistence.Transient

class NewsFeed implements InGameStuff {

    GameAclService gameAclService

    String title
    static belongsTo = [game: Game]

    static hasMany = [newsItems: NewsItem]

    static transients = ['gameAclService']

    static mapping = {
        newsItems sort: 'created', order: 'desc'
    }
    static constraints = {
        // TODO make it case-insensitive
        title validator: {val, obj ->
            NewsFeed.findByGameAndTitleIlikeAndIdNotEqual(obj.game, val, obj.id) == null}
    }

    def afterInsert()
    {
        gameAclService.createAcl(this)
    }

    @Override
    Game extractGame() {
        return game
    }
}
