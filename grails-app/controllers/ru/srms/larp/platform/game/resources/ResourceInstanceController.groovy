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

  static allowedMethods = [save: "POST", update: "POST"]

  def show(ResourceInstance resource) {
    respond resourceService.getResourceInstance(resource)
  }

  def create() {
    def type = GameResource.get(params.typeId)
    respond resourceService.createResourceInstance(type)
  }

  def edit(ResourceInstance resource) {
    respond resourceService.editResourceInstance(resource)
  }

  @Transactional
  def save(ResourceInstance resource) {
    if (validateData(resource, 'create')) {
      resourceService.saveResourceInstance(resource)
      respondChange('default.created.message', CREATED, resource)
    }
  }

  @Transactional
  def update(GameResource resource) {
    if (validateData(resource, 'edit')) {
      resourceService.saveResourceInstance(resource)
      respondChange('default.updated.message', OK, resource)
    }
  }

  @Transactional
  def delete(GameResource resource) {
    if(validateData(resource)) {
      resourceService.deleteResourceInstance(resource)
      respondChange('default.deleted.message', NO_CONTENT, null, resource.id)
    }
  }

  @Override
  protected String labelCode() {
    return 'resourceInstance.label'
  }
}
