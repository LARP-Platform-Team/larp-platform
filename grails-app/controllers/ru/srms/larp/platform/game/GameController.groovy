package ru.srms.larp.platform.game

import grails.plugin.springsecurity.acl.AclService
import grails.plugin.springsecurity.acl.AclUtilService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.core.context.SecurityContextHolder
import ru.srms.larp.platform.sec.SpringUser

import java.security.BasicPermission

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GameController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService
    def aclUtilService

    @Secured(['permitAll'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Game.list(params), model:[gameInstanceCount: Game.count()]
        println springSecurityService.currentUser?.username
        println SecurityContextHolder.getContext().getAuthentication()?.getName()


        println aclUtilService.hasPermission(SecurityContextHolder.getContext().getAuthentication()
                , Game.get(9), BasePermission.ADMINISTRATION)

    }

    @Transactional
    @Secured(['permitAll'])
    def show(Game gameInstance) {
        respond gameInstance
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create() {
        respond new Game(params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @PreAuthorize("hasPermission(#gameInstance, 'ADMINISTRATION')")
    def edit(Game gameInstance) {
        respond gameInstance
    }

    @PreAuthorize("hasPermission(#gameInstance, ADMINISTRATION)")
    def edit1(Game gameInstance) {
        respond gameInstance
    }

    @PreAuthorize("hasPermission(#gameInstance, 'ADMINISTRATION')")
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

        gameInstance.save flush:true

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
