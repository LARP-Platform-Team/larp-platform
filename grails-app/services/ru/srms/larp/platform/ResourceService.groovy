package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.resources.GameResource
import ru.srms.larp.platform.game.roles.GameRole

@Transactional(readOnly = true)
class ResourceService {

  @PreAuthorize("hasPermission(#game, admin)")
  def list(Game game, Map pagination) {
    def game1 = GameResource.findByGame(game, pagination)
    return game1
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def count(Game game) {
    GameResource.countByGame(game)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def create(Game game) {
    new GameResource(game: game)
  }

  @PreAuthorize("hasPermission(#resource.game, admin)")
  def edit(GameResource resource) { resource }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def save(GameRole resource) {
    resource.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def delete(GameRole resource) {
    resource.delete()
  }




}
