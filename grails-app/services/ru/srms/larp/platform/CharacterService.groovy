package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostAuthorize
import ru.srms.larp.platform.game.character.GameCharacter

@Transactional
class CharacterService {

    @PostAuthorize("returnObject == null or hasPermission(returnObject, read) or \
                    hasPermission(returnObject.game, admin)")
    GameCharacter find(String alias) {
        GameCharacter.findByAlias(alias)
    }
}
