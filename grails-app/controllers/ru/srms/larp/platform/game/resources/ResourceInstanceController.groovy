package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class ResourceInstanceController extends BaseModuleController {

  ResourceService resourceService

  static allowedMethods = [save: "POST", update: "POST", changeValue: "POST", transfer: "POST"]

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
      if (validateData(resource, 'show')) {
        resourceService.updateResourceValue(resource)
        respondChange('Вы успешно изменили значение', OK,
            [action: 'show', id: resource.id])
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
      respondChange('Перевод успешно осуществлен', OK,
          [action: 'show', id: source.id])
    }
  }

  @Transactional
  def save(ResourceInstance resource) {
    withModule {
      if (validateData(resource, 'create')) {
        resourceService.saveResourceInstance(resource)
        respondChange('Экземпляр ресурса успешно создан', CREATED,
            [controller: 'GameResource', action: 'show', id: resource.type.id])
      }
    }
  }

  @Transactional
  def update() {
    withModule {
      // saving old params to change permission correctly
      def resource = ResourceInstance.get(params.id)
      def oldResource = new HashMap<>(resource.properties)
      resource.properties = params

      if (validateData(resource, 'edit')) {
        resourceService.saveResourceInstance(resource, oldResource)
        respondChange('Экземпляр ресурса обновлен', OK,
            [controller: 'GameResource', action: 'show', id: resource.type.id])
      }
    }
  }

  @Transactional
  def delete(ResourceInstance resource) {
    withModule {
      if (validateData(resource)) {
        resourceService.deleteResourceInstance(resource)
        respondChange('Экземпляр ресурса удален', NO_CONTENT,
            [controller: 'GameResource', action: 'show', id: resource.type.id])
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.RESOURCES
  }
}
