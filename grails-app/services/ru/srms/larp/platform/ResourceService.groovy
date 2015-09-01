package ru.srms.larp.platform

import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.quartz.CronScheduleBuilder
import org.quartz.TriggerBuilder
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.resources.*

import static org.springframework.security.acls.domain.BasePermission.*

@Transactional(readOnly = true)
class ResourceService {

  GameAclService gameAclService
  AclUtilService aclUtilService
  def quartzScheduler

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

  @PostFilter("hasPermission(#game, admin) or hasPermission(filterObject, read)")
  def findInstancesByGame(Game game) {
    ResourceInstance.getAllByGame(game)
  }

  @Transactional
  @PreAuthorize("hasPermission(#resource.game, admin)")
  def deleteOrigin(GameResource resource, ResourceOrigin origin) {
    resource.removeFromOrigins(origin)
    resource.save()
    origin.delete()
  }

  @PreAuthorize("hasPermission(#resource.extractGame(), admin) or hasPermission(#resource, read)")
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
  @PreAuthorize("hasPermission(#resource.extractGame(), admin) or hasPermission(#resource, write)")
  def updateResourceValue(ResourceInstance resource) {
    resource.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#data.source.extractGame(), admin) or hasPermission(#data.source, create)")
  def transfer(TransferData data) {
    def source = data.source
    def target = ResourceInstance.findByIdentifierAndType(data.transferTargetId, source.type)
    if (!target)
      throw new Exception("No transfer target was found")

    source.value -= data.transferValue
    source.save(flush: true)

    target.value += data.transferValue
    target.save(flush: true)

    // save a log entry
    TransferLogEntry logEntry = new TransferLogEntry(
        value: data.transferValue, comment: data.comment, source: source, target: target,
        sourceName: source.fullId, targetName: target.fullId)
    if (logEntry.validate())
      logEntry.save(flush: true)
  }


  @Transactional
  @PreAuthorize("hasPermission(#resource.extractGame(), admin)")
  def deleteResourceInstance(ResourceInstance resource) {
    resource.delete()
  }

  @Transactional
  @PreAuthorize("hasPermission(#target.extractGame(), admin)")
  def createPeriodicRule(ResourceInstance target) {
    new PeriodicRule(target: target)
  }

  @Transactional
  @PreAuthorize("hasPermission(#rule.extractGame(), admin)")
  def editPeriodicRule(PeriodicRule rule) { rule }

  @Transactional
  @PreAuthorize("hasPermission(#rule.extractGame(), admin)")
  def savePeriodicRule(PeriodicRule rule) {
    boolean insert = rule.id == null
    rule.save(flush: true)
    addTriggerForRule(rule, insert)
  }

  @Transactional
  @PreAuthorize("hasPermission(#rule.extractGame(), admin)")
  def deletePeriodicRule(PeriodicRule rule) {
    rule.delete()
  }

  private def addTriggerForRule(PeriodicRule rule, boolean isNew)
  {
    def days = rule.fireDays.collect { it.code }.toArray(new int[rule.fireDays.size()])
    if(days.length == 0)
      throw new Exception("Days array is empty")

    def trigger = TriggerBuilder.newTrigger()
        .withIdentity(rule.triggerKey)
        .forJob(PeriodicResourceUpdateJob.jobKey)
        .usingJobData("ruleId", rule.id)
        .withSchedule(
          CronScheduleBuilder
          .atHourAndMinuteOnGivenDaysOfWeek(rule.fireHour, rule.fireMinute, days)
//        .inTimeZone(TimeZone)
    )
        .startNow()
        .build()

    if(isNew)
      quartzScheduler.scheduleJob(trigger)
    else
      quartzScheduler.rescheduleJob(rule.triggerKey, trigger)

    println "Next time: ${trigger.getNextFireTime()}"
  }

  private def removeTriggerForRule(PeriodicRule rule) {
    quartzScheduler.unscheduleJob(rule.triggerKey)
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
    // init null object
    if (oldResource == null)
      oldResource = [owner: null]

    if (oldResource.owner) deletePermissions(resource, oldResource)
    if (resource.owner) addPermissions(resource, oldResource)
  }
}