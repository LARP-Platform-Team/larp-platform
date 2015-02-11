package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game

@Transactional
class GameService {

    @PreAuthorize("hasPermission(#gameInstance, 'ADMINISTRATION') \
                    or hasRole('ROLE_ADMIN')")
    def update(Game gameInstance) {
        gameInstance.save flush:true
    }

    @PreAuthorize("hasPermission(#id, 'ru.srms.larp.platform.game.Game', 'ADMINISTRATION') \
                    or hasRole('ROLE_ADMIN')")
    Game edit(Long id) {
        return Game.get(id)
    }
}
