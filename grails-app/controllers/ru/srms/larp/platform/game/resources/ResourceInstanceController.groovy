package ru.srms.larp.platform.game.resources

import grails.plugin.quartz2.TriggerHelper
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.quartz.JobKey
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class ResourceInstanceController extends BaseModuleController {

  ResourceService resourceService
  def quartzScheduler

  static allowedMethods = [save: "POST", update: "POST", changeValue: "POST", transfer: "POST"]
  public static final String PERIODIC_UPDATE_JOB_KEY = "PeriodicResourceUpdate"

  def show(ResourceInstance resource) {
    withModule {
      respond resourceService.getResourceInstance(resource)
    }
  }

  def create() {
    withModule {
      if (!GameResource.exists(params.typeId))
        throw new Exception("Неверный тип ресурсы")
      def type = GameResource.get(params.typeId)
      respond resourceService.createResourceInstance(type)
    }
  }

  def edit(ResourceInstance resource) {
    withModule {
      respond resourceService.editResourceInstance(resource)
    }
  }

  @Transactional
  def changeValue() {
    withModule {
      def resource = ResourceInstance.get(params.id)
      resource.properties['value'] = params
      params.redirectToParent = false
      if (validateData(resource, 'show')) {
        resourceService.updateResourceValue(resource)
        respondChange('Вы успешно изменили значение', OK, resource)
      }
    }
  }

  @Transactional
  def transfer(TransferData data) {
    withModule {
      def source = ResourceInstance.get(params.id)
      data.source = source
      if (!data.validate()) {
        respond source, model: [transferData: data], view: 'show'
        return
      }


      resourceService.transfer(data)

      params.redirectToParent = false
      respondChange('Перевод успешно осуществлен', OK, source)
    }
  }

  @Transactional
  def save(ResourceInstance resource) {
    withModule {
      if (validateData(resource, 'create')) {
        params.redirectToParent = true
        resourceService.saveResourceInstance(resource)
        respondChange('Экземпляр ресурса успешно создан', CREATED, resource)
      }
    }
  }

  @Transactional
  def update() {
    withModule {
      params.redirectToParent = true
      // saving old params to change permission correctly
      def resource = ResourceInstance.get(params.id)
      def oldResource = new HashMap<>(resource.properties)
      resource.properties = params

      if(params.quartz) {
        println "Schedulgin ${params.quartz}"


        def trigger = TriggerHelper.simpleTrigger(
            JobKey.jobKey(PERIODIC_UPDATE_JOB_KEY, "LarpPlatform"),
            new Date(new Date().time + 2000), 500, 2000, [theProp: params.quartz])

        quartzScheduler.scheduleJob(trigger)

      }

      if (validateData(resource, 'edit')) {
        resourceService.saveResourceInstance(resource, oldResource)
        respondChange('Экземпляр ресурса обновлен', OK, resource)
      }
    }
  }

  @Transactional
  def delete(ResourceInstance resource) {
    withModule {
      if (validateData(resource)) {
        // save feed id to params for redirect
        params.redirectToParent = true
        params.type = [:]
        params.type.id = resource.type.id

        resourceService.deleteResourceInstance(resource)
        respondChange('Экземпляр ресурса удален', NO_CONTENT, null, resource.id)
      }
    }
  }

  @Override
  protected Map redirectParams() {
    def attrs = super.redirectParams()
    if (params.redirectToParent) {
      attrs.controller = 'GameResource'
      attrs.action = 'show'
      attrs.id = params.type.id
    } else {
      attrs.action = 'show'
      attrs.id = params.id
    }
    return attrs
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.RESOURCES
  }
}
