package ru.srms.larp.platform.game

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameController {

    static allowedMethods = [save: "POST", update: "POST"]
    def gameService

    @Secured(['permitAll'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Game.list(params), model:[gameInstanceCount: Game.count()]
    }

    @Secured(['permitAll'])
    def play() {
        respond Game.findByAlias(params.alias)
    }

    def create() {
        respond new Game(params)
    }

    @Transactional
    def save(Game gameInstance) {
        if (gameInstance == null) {
            notFound()
            return
        }

        if (gameInstance.hasErrors()) {
            respond gameInstance.errors, view:'create'
            return
        }

        gameInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'game.label', default: 'Game'), gameInstance.id])
                redirect gameInstance
            }
            '*' { respond gameInstance, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond gameService.edit(id)
    }

    @Transactional
    def update(Game gameInstance) {
        if (gameInstance == null) {
            notFound()
            return
        }

        if (gameInstance.hasErrors()) {
            respond gameInstance.errors, view:'edit'
            return
        }

        gameService.update(gameInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Game.label', default: 'Game'), gameInstance.id])
                redirect gameInstance
            }
            '*'{ respond gameInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(Game gameInstance) {

        if (gameInstance == null) {
            notFound()
            return
        }

        gameInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Game.label', default: 'Game'), gameInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['permitAll'])
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
