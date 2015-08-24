package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.ResourceService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class ResourceInstanceController extends BaseController {

  ResourceService resourceService

  static allowedMethods = [save: "POST", update: "POST", changeValue: "POST", transfer: "POST"]

  def show(ResourceInstance resource) {
    respond resourceService.getResourceInstance(resource)
  }

  def create() {
    if(!GameResource.exists(params.typeId))
      throw new Exception("Неверный тип ресурсы")
    def type = GameResource.get(params.typeId)
    respond resourceService.createResourceInstance(type)
  }

  def edit(ResourceInstance resource) {
    respond resourceService.editResourceInstance(resource)
  }

  @Transactional
  def changeValue() {
    def resource = ResourceInstance.get(params.id)
    resource.properties['value'] = params
    params.redirectToParent = false
    if (validateData(resource, 'show')) {
      resourceService.updateResourceValue(resource)
      respondChange('Вы успешно изменили значение', OK, resource)
    }
  }

  @Transactional
  def transfer(TransferData data) {
    def source = ResourceInstance.get(params.id)
    data.source = source
    if(!data.validate()) {
      respond source, model: [transferData: data], view: 'show'
      return
    }

    resourceService.transfer(data)

    params.redirectToParent = false
    respondChange('Перевод успешно осуществлен', OK, source)
  }

  @Transactional
  def save(ResourceInstance resource) {
    if (validateData(resource, 'create')) {
      params.redirectToParent = true
      resourceService.saveResourceInstance(resource)
      respondChange('Экземпляр ресурса успешно создан', CREATED, resource)
    }
  }

  @Transactional
  def update() {
      params.redirectToParent = true
      // saving old params to change permission correctly
      def resource = ResourceInstance.get(params.id)
      def oldResource = new HashMap<>(resource.properties)
      resource.properties = params

    if (validateData(resource, 'edit')) {
      resourceService.saveResourceInstance(resource, oldResource)
      respondChange('Экземпляр ресурса обновлен', OK, resource)
    }
  }

  @Transactional
  def delete(ResourceInstance resource) {
    if(validateData(resource)) {
      // save feed id to params for redirect
      params.redirectToParent = true
      params.type = [:]
      params.type.id = resource.type.id

      resourceService.deleteResourceInstance(resource)
      respondChange('Экземпляр ресурса удален', NO_CONTENT, null, resource.id)
    }
  }

  @Override
  protected Map redirectParams() {
    def attrs = super.redirectParams()
    if(params.redirectToParent) {
      attrs.controller = 'GameResource'
      attrs.action = 'show'
      attrs.id = params.type.id
    }
    else {
      attrs.action = 'show'
      attrs.id = params.id
    }
    return attrs
  }

}
