package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.resources.GameResource
import ru.srms.larp.platform.game.resources.ResourceInstance
import ru.srms.larp.platform.game.resources.ResourceOrigin

@Transactional(readOnly = true)
class ResourceService {

  @PreAuthorize("hasPermission(#game, admin)")
  def listResources(Game game, Map pagination) {
    GameResource.findAllByGame(game, pagination)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def countResources(Game game) {
    GameResource.countByGame(game)
  }

  @PreAuthorize("hasPermission(#resource.game, admin)")
  def getResource(GameResource resource) { resource }

  @PreAuthorize("hasPermission(#game, admin)")
  def createResource(Game game) {
    new GameResource(game: game)
  }

  @PreAuthorize("hasPermission(#resource.game, admin)")
  def editResource(GameResource resource) { resource }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def saveResource(GameResource resource) {
    resource.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def deleteResource(GameResource resource) {
    resource.delete()
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def addOrigin(GameResource resource, ResourceOrigin origin) {
    origin.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def deleteOrigin(GameResource resource, ResourceOrigin origin) {
    resource.removeFromOrigins(origin)
    resource.save()
    origin.delete()
  }

  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def getResourceInstance(ResourceInstance resource) { resource }

  @PreAuthorize("hasPermission(#type.game, admin)")
  def createResourceInstance(GameResource type) {
    new ResourceInstance(type: type)
  }

  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def editResourceInstance(ResourceInstance resource) { resource }

  @Transactional
  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def saveResourceInstance(ResourceInstance resource) {
    resource.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def deleteResourceInstance(ResourceInstance resource) {
    resource.delete()
  }
}