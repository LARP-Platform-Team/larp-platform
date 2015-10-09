package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.game.roles.CharacterRole
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
  def save(GameRole role) {
    role.save()
  }

  @PreAuthorize("hasPermission(#role.game, admin)")
  def delete(GameRole role) {
    if (role.parent)
      role.parent.removeFromSubRoles(role)
    role.delete()
  }

  @PreAuthorize("hasPermission(#role.game, admin)")
  def add(GameRole role, GameCharacter character) {
    addAllRoles(role, character)
  }

  @PreAuthorize("hasPermission(#role.game, admin)")
  def edit(GameRole role) { role }

  @PreAuthorize("hasPermission(#role.game, admin)")
  def remove(GameRole role, GameCharacter character) {
    removeAllRoles(role, character)
  }

  private def addAllRoles(GameRole role, GameCharacter character) {
    if (!CharacterRole.exists(CharacterRole.id(character, role)))
      CharacterRole.create(character, role);
    if (role.parent)
      addAllRoles(role.parent, character)
  }

  private def removeAllRoles(GameRole role, GameCharacter character) {
    if (CharacterRole.exists(CharacterRole.id(character, role)))
      CharacterRole.remove(character, role);
    if (role.subRoles)
      role.subRoles.each { removeAllRoles(it, character) }
  }

}
