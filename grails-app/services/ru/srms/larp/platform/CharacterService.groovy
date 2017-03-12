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

    private String like(String value) {
        if(value.isEmpty())
            return value

        if(value.charAt(0) != '%')
            value = "%" + value
        if(value.charAt(value.length() - 1) != '%')
            value = value + "%"

        return value
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def list(Game game, String findName, Map pagination) {
        return findName.isEmpty() ?
            GameCharacter.findAllByGame(game, pagination) :
            GameCharacter.findAllByGameAndNameIlike(game, this.like(findName), pagination);
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def count(Game game, String findName) {
        return findName.isEmpty() ?
            GameCharacter.countByGame(game) :
            GameCharacter.countByGameAndNameIlike(game, this.like(findName));
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

            if(GameCharacter.countByPlayerAndGame(oldPlayer, character.game) == 0
            && !character.game.invitedPlayers.collect {it.id}.contains(oldPlayer.id)) {
                aclUtilService.deletePermission(character.game, oldPlayer.username, READ)
            }

        }

        if (newPlayer) {
            aclUtilService.addPermission(character, newPlayer.username, READ)
            aclUtilService.addPermission(character, newPlayer.username, WRITE)

            if (GameCharacter.countByPlayerAndGame(newPlayer, character.game) == 1
                && !character.game.invitedPlayers.collect { it.id }.contains(newPlayer.id)) {
                aclUtilService.addPermission(character.game, newPlayer.username, READ)
            }
        }
    }

    @PreAuthorize("hasPermission(#character.game, admin)")
    def delete(GameCharacter character) {
        aclUtilService.deleteAcl(character)
        if(character.player) {
            if (GameCharacter.countByPlayerAndGame(character.player, character.game) == 1
                && !character.game.invitedPlayers.collect { it.id }.contains(character.player.id)) {
                aclUtilService.deletePermission(character.game, character.player.username, READ)
            }
        }
        character.delete()
    }
}
