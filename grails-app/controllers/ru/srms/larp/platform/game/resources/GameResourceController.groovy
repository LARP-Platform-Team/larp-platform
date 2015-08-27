package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.exceptions.AjaxException
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

// TODO make generic Controllers with mainService property (class of CRUDSerivce e.g.)
@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class GameResourceController extends BaseModuleController {

  ResourceService resourceService

  static allowedMethods = [save: "POST", update: "POST"]

  def index() {
    withModule {
      respond resourceService.listResources(params.game, paginator()),
          model: [itemsCount: resourceService.countResources(params.game)]
    }
  }

  def show(GameResource resource) {
    withModule {
      respond resourceService.getResource(resource)
    }
  }

  def create() {
    withModule {
      respond resourceService.createResource(params.game)
    }
  }

  def edit(GameResource resource) {
    withModule {
      respond resourceService.editResource(resource)
    }
  }

  @Transactional
  def save(GameResource resource) {
    withModule {
      if (validateData(resource, 'create')) {
        resourceService.saveResource(resource)
        respondChange('Игровой ресурс успешно добавлен', CREATED, resource)
      }
    }
  }

  @Transactional
  def update(GameResource resource) {
    withModule {
      if (validateData(resource, 'edit')) {
        resourceService.saveResource(resource)
        respondChange('Игровой ресурс обновлен', OK, resource)
      }
    }
  }

  @Transactional
  def delete(GameResource resource) {
    withModule {
      if (validateData(resource)) {
        resourceService.deleteResource(resource)
        respondChange('Игровой ресурс удален', NO_CONTENT, null, resource.id)
      }
    }
  }

  @Transactional
  def addOrigin(GameResource resource, String originTitle) {
    doAjax {
      withModule {
        def origin = new ResourceOrigin(title: originTitle, resource: resource)
        if (origin.hasErrors())
          throw new AjaxException(g.renderErrors(bean: origin).toString())

        def result = resourceService.addOrigin(resource, origin)
        if (result == null && origin.hasErrors()) {
          throw new AjaxException(g.renderErrors(bean: origin).toString())
        }

        render template: 'origins', model: [items: resource.origins]
      }
    }
  }

  @Transactional
  def deleteOrigin(ResourceOrigin origin) {
    doAjax {
      withModule {
        if (ResourceInstance.countByOrigin(origin) > 0)
          throw new AjaxException("Невозможно удалить источник, т.к. он используется")
        def resource = origin.resource
        resourceService.deleteOrigin(resource, origin)
        render template: 'origins', model: [items: resource.origins]
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.RESOURCES
  }
}
