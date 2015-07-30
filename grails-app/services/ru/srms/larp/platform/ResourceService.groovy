package ru.srms.larp.platform

import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.resources.GameResource
import ru.srms.larp.platform.game.resources.ResourceInstance
import ru.srms.larp.platform.game.resources.ResourceOrigin

import static org.springframework.security.acls.domain.BasePermission.*

@Transactional(readOnly = true)
class ResourceService {

  GameAclService gameAclService
  AclUtilService aclUtilService

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
  def saveResourceInstance(ResourceInstance resource, Object oldResource = null) {
    boolean insert = resource.id == null
    resource.save()
    if (insert) gameAclService.createAcl(resource)
    changePermissions(resource, oldResource)
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def deleteResourceInstance(ResourceInstance resource) {
    resource.delete()
  }

  private def deletePermissions(ResourceInstance resource, Map oldResource) {
    def permissions = oldResource.owner == resource.owner ?
        // если владелец остался прежним
        [(READ)  : false,
         (WRITE) : oldResource.ownerEditable && !resource.ownerEditable,
         (CREATE): oldResource.transferable && !resource.transferable] :
        // если владелец поменялся
        [(READ): true, (WRITE): oldResource.ownerEditable, (CREATE): oldResource.transferable]

    permissions.findAll { it.value }.each {
      aclUtilService.deletePermission(resource, oldResource.owner.authority, it.key)
    }
  }

  private def addPermissions(ResourceInstance resource, Map oldResource) {
    def permissions = oldResource.owner == resource.owner ?
        // если владелец остался прежним (и он не null, т.к. метод вызывается только в этом случае)
        [(READ)  : false,
         (WRITE) : !oldResource.ownerEditable && resource.ownerEditable,
         (CREATE): !oldResource.transferable && resource.transferable] :
        // если владелец поменялся
        [(READ): true, (WRITE): resource.ownerEditable, (CREATE): resource.transferable]

    permissions.findAll { it.value }.each {
      aclUtilService.addPermission(resource, resource.owner.authority, it.key)
    }
  }

  private def changePermissions(ResourceInstance resource, Map oldResource) {
    if (oldResource.owner) deletePermissions(resource, oldResource)
    if (resource.owner) addPermissions(resource, oldResource)
  }
}