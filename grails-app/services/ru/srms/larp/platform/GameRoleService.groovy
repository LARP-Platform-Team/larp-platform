package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.roles.GameRole

@Transactional
class GameRoleService {

    @PreAuthorize("hasPermission(#game, admin)")
    def list(Game game, GameRole parent, Map pagination) {
        GameRole.findAllByGameAndParent(game, parent, pagination)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def count(Game game, GameRole parent) {
        GameRole.countByGameAndParent(game, parent)
    }
}
