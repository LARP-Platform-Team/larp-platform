package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game

@Transactional
class GameService {

    @PreAuthorize("hasPermission(#gameInstance, 'ADMINISTRATION')")
    def update(Game gameInstance) {
        gameInstance.save flush:true
    }
}
