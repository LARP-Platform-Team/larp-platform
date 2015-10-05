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
  def gameAclService

  @PostFilter("hasPermission(filterObject, admin) || \
      !filterObject.confidential || hasPermission(filterObject, read)")
  List<Game> list(Map pagination = null) {
    Game.list(pagination)
  }

  def count() {
    list().size()
  }

  @PreAuthorize("hasPermission(#game, admin) || \
      !#game.confidential || hasPermission(#game, read)")
  def play(Game game) { game }

  def save(Game game) {
    boolean insert = game.id == null
    game.save flush: true
    if (insert) {
      gameAclService.createAcl(game)
      addGameMaster(game, springSecurityService.currentUser)
    }
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def update(Game game) {
    game.save flush: true
  }

  @PreAuthorize("hasPermission(#game, admin)")
  Game edit(Game game) {
    game
  }

  @PostFilter("hasPermission(filterObject, read) or \
                 hasPermission(#game, admin)")
  Set<GameCharacter> getAvailableCharacters(Game game) {
    game.characters ?: Collections.EMPTY_SET
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def addMaster(Game game, SpringUser master) {
    addGameMaster(game, master)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def removeMaster(Game game, SpringUser master) {
    if (!game.masters.contains(master))
      throw new RuntimeException("Этот мастер не существует.")

    if (master.id == springSecurityService.currentUser.id)
      throw new RuntimeException("Нельзя удалить себя.")

    game.removeFromMasters(master)
    game.save()

    // remove acl-permission from user
    aclUtilService.deletePermission(game, master.username, BasePermission.ADMINISTRATION)
  }

  private def addGameMaster(Game game, SpringUser master) {
    if (game.masters && game.masters.contains(master))
      throw new RuntimeException("Мастер уже назначен на игру.")
    game.addToMasters(master)
    game.save()

    // TODO использовать ROLE_GM для людей,которые могут СОЗДАВАТЬ игры
//        def roleGm = SpringRole.findByAuthority(SpringRole.GAME_MASTER_ROLE)
//        if(!player.authorities.contains(roleGm))
//            SpringUserSpringRole.create(player, roleGm)

    def roleAclEditor = SpringRole.findByAuthority(SpringRole.ACL_EDITOR_ROLE)
    if (!master.authorities.contains(roleAclEditor))
      SpringUserSpringRole.create(master, roleAclEditor)

    // add acl-permission to user
    aclUtilService.addPermission(game, master.username, BasePermission.ADMINISTRATION)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def invitePlayer(Game game, SpringUser player) {
    if (game.invitedPlayers && game.invitedPlayers.contains(player))
      throw new RuntimeException("Пользователь уже приглашен в игру")
    if (game.masters && game.masters.contains(player))
      throw new RuntimeException("Пользователь является мастером")
    if (player.id == springSecurityService.currentUser.id)
      throw new RuntimeException("Нельзя пригласить себя")

    game.addToInvitedPlayers(player)
    game.save()

    if (GameCharacter.countByPlayerAndGame(player, game) == 0)
      aclUtilService.addPermission(game, player.username, BasePermission.READ)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def removePlayer(Game game, SpringUser player) {
    if (!game.invitedPlayers.contains(player))
      throw new RuntimeException("Пользователь не был приглашен в игру.")

    game.removeFromInvitedPlayers(player)
    game.save()

    if (GameCharacter.countByPlayerAndGame(player, game) == 0)
      aclUtilService.deletePermission(game, player.username, BasePermission.READ)
  }

}
