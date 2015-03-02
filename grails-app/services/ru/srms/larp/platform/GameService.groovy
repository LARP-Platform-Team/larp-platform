package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

@Transactional
class GameService {

    def save(Game game) {
        game.save flush:true
    }

    @PreAuthorize("hasPermission(#game, admin) or hasRole('ROLE_ADMIN')")
    def update(Game game) {
        game.save flush:true
    }

    @PreAuthorize("hasPermission(#id, 'ru.srms.larp.platform.game.Game', admin) \
                    or hasRole('ROLE_ADMIN')")
    Game edit(Long id) {
        Game.get(id)
    }

    @PostFilter("hasPermission(filterObject, read) or \
                 hasPermission(#game, admin)")
    Set<GameCharacter> getAvailableCharacters(Game game) {
        game.characters ?: Collections.EMPTY_SET
    }
}
