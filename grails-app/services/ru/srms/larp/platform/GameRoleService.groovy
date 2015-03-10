package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
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

    @PreAuthorize("hasPermission(#game, admin)")
    def create(Game game, GameRole parent) {
        new GameRole(game: game, parent: parent)
    }

    @PreAuthorize("hasPermission(#role.game, admin)")
    def add(GameRole role, GameCharacter character) {
        role.addToCharacters(character)
        role.save(flush: true)
    }

    @PreAuthorize("hasPermission(#role.game, admin)")
    def remove(GameRole role, GameCharacter character) {
        role.removeFromCharacters(character)
        role.save(flush: true)
    }
}
