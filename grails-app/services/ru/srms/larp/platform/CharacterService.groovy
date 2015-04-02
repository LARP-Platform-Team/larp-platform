package ru.srms.larp.platform

import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

import static org.springframework.security.acls.domain.BasePermission.READ
import static org.springframework.security.acls.domain.BasePermission.WRITE

@Transactional
class CharacterService {

    GameAclService gameAclService
    AclUtilService aclUtilService

    @PostAuthorize("returnObject == null or hasPermission(returnObject, read) or \
                    hasPermission(returnObject.game, admin)")
    GameCharacter find(String alias) {
        GameCharacter.findByAlias(alias)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def list(Game game, Map pagination) {
        GameCharacter.findAllByGame(game, pagination)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def count(Game game) {
        GameCharacter.countByGame(game)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def create(Game game) {
        new GameCharacter(game: game)
    }

    @PreAuthorize("hasPermission(#character.game, admin)")
    def edit(GameCharacter character) { character }

    @PreAuthorize("hasPermission(#character.game, admin)")
    def save(GameCharacter character, SpringUser oldPlayer = null) {
        boolean insert = character.id == null
        character.save()
        if (insert) gameAclService.createAcl(character)
        changePermissions(character, oldPlayer, character.player)
    }

    private def changePermissions(
            GameCharacter character, SpringUser oldPlayer, SpringUser newPlayer) {
        if (oldPlayer == newPlayer)
            return

        if (oldPlayer) {
            aclUtilService.deletePermission(character, oldPlayer.username, READ)
            aclUtilService.deletePermission(character, oldPlayer.username, WRITE)
        }

        if (newPlayer) {
            aclUtilService.addPermission(character, newPlayer.username, READ)
            aclUtilService.addPermission(character, newPlayer.username, WRITE)
        }
    }

    @PreAuthorize("hasPermission(#character.game, admin)")
    def delete(GameCharacter character) {
        aclUtilService.deleteAcl(character)
        character.delete()
    }
}
