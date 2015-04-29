package ru.srms.larp.platform.game.resources

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class GameResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond GameResource.list(params), model:[gameResourceInstanceCount: GameResource.count()]
    }

    def show(GameResource gameResourceInstance) {
        respond gameResourceInstance
    }

    def create() {
        respond new GameResource(params)
    }

    @Transactional
    def save(GameResource gameResourceInstance) {
        if (gameResourceInstance == null) {
            notFound()
            return
        }

        if (gameResourceInstance.hasErrors()) {
            respond gameResourceInstance.errors, view:'create'
            return
        }

        gameResourceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gameResource.label', default: 'GameResource'), gameResourceInstance.id])
                redirect gameResourceInstance
            }
            '*' { respond gameResourceInstance, [status: CREATED] }
        }
    }

    def edit(GameResource gameResourceInstance) {
        respond gameResourceInstance
    }

    @Transactional
    def update(GameResource gameResourceInstance) {
        if (gameResourceInstance == null) {
            notFound()
            return
        }

        if (gameResourceInstance.hasErrors()) {
            respond gameResourceInstance.errors, view:'edit'
            return
        }

        gameResourceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'GameResource.label', default: 'GameResource'), gameResourceInstance.id])
                redirect gameResourceInstance
            }
            '*'{ respond gameResourceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(GameResource gameResourceInstance) {

        if (gameResourceInstance == null) {
            notFound()
            return
        }

        gameResourceInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'GameResource.label', default: 'GameResource'), gameResourceInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gameResource.label', default: 'GameResource'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
