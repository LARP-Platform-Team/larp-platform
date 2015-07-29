package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.exceptions.AjaxException

import static org.springframework.http.HttpStatus.*

// TODO make generic Controllers with mainService property (class of CRUDSerivce e.g.)
@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class GameResourceController extends BaseController {

    ResourceService resourceService

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        respond resourceService.listResources(params.game, paginator()),
            model:[itemsCount: resourceService.countResources(params.game)]
    }

    def show(GameResource resource) {
        respond resourceService.getResource(resource)
    }

    def create() {
        respond resourceService.createResource(params.game)
    }

    def edit(GameResource resource) {
        respond resourceService.editResource(resource)
    }

    @Transactional
    def save(GameResource resource) {
        if (validateData(resource, 'create')) {
            resourceService.saveResource(resource)
            respondChange('default.created.message', CREATED, resource)
        }
    }

    @Transactional
    def update(GameResource resource) {
        if (validateData(resource, 'edit')) {
            resourceService.saveResource(resource)
            respondChange('default.updated.message', OK, resource)
        }
    }

    @Transactional
    def delete(GameResource resource) {
        if(validateData(resource)) {
            resourceService.deleteResource(resource)
            respondChange('default.deleted.message', NO_CONTENT, null, resource.id)
        }
    }

    @Transactional
    def addOrigin(GameResource resource, String originTitle) {
        doAjax {

            def origin = new ResourceOrigin(title: originTitle, resource: resource)
            if(origin.hasErrors())
                throw new AjaxException(g.renderErrors(bean: origin).toString())

            def result = resourceService.addOrigin(resource, origin)
            if(result == null && origin.hasErrors()) {
                throw new AjaxException(g.renderErrors(bean: origin).toString())
            }

            render template: 'origins', model: [origins: resource.origins]
        }
    }

    @Transactional
    def deleteOrigin(ResourceOrigin origin) {
        doAjax {
            def resource = origin.resource
            resourceService.deleteOrigin(resource, origin)
            render template: 'origins', model: [origins: resource.origins]
        }
    }

    @Override
    protected String labelCode() {
        return 'resource.label'
    }
}
