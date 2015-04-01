package ru.srms.larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringRole
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.SpringUserSpringRole

@Transactional
class GameService {

    SpringSecurityService springSecurityService
    def aclUtilService

    def save(Game game) {
        game.save flush:true
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def update(Game game) {
        game.save flush:true
    }

    @PreAuthorize("hasPermission(#id, 'ru.srms.larp.platform.game.Game', admin)")
    Game edit(Long id) {
        Game.get(id)
    }

    @PostFilter("hasPermission(filterObject, read) or \
                 hasPermission(#game, admin)")
    Set<GameCharacter> getAvailableCharacters(Game game) {
        game.characters ?: Collections.EMPTY_SET
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def addMaster(Game game, SpringUser master) {
        if(game.masters.contains(master))
            throw new RuntimeException("Мастер уже назначен на игру.")
        game.addToMasters(master)
        game.save()

        // maybe we need to add roles to user - depends on game-master future policy
        def roleGm = SpringRole.findByAuthority(SpringRole.GAME_MASTER_ROLE)
        def roleAclEditor = SpringRole.findByAuthority(SpringRole.ACL_EDITOR_ROLE)
        if(!master.authorities.contains(roleGm))
            SpringUserSpringRole.create(master, roleGm)
        if(!master.authorities.contains(roleAclEditor))
            SpringUserSpringRole.create(master, roleAclEditor)

        // add acl-permission to user
        aclUtilService.addPermission(game, master.username, BasePermission.ADMINISTRATION)
    }

    @PreAuthorize("hasPermission(#game, admin)")
    def removeMaster(Game game, SpringUser master) {
        if(!game.masters.contains(master))
            throw new RuntimeException("Этот мастер не существует.")

        if(master.id == springSecurityService.currentUser.id)
            throw new RuntimeException("Нельзя удалить себя.")

        game.removeFromMasters(master)
        game.save()

        // remove acl-permission from user
        aclUtilService.deletePermission(game, master.username, BasePermission.ADMINISTRATION)
    }
}
