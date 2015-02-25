package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(['permitAll'])
@Transactional(readOnly = true)
class NewsFeedController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NewsFeed.list(params), model:[newsFeedInstanceCount: NewsFeed.count()]
    }

    def show(NewsFeed newsFeedInstance) {
        respond newsFeedInstance
    }

    def create() {
        respond new NewsFeed(params)
    }

    @Transactional
    def save(NewsFeed newsFeedInstance) {
        if (newsFeedInstance == null) {
            notFound()
            return
        }

        if (newsFeedInstance.hasErrors()) {
            respond newsFeedInstance.errors, view:'create'
            return
        }

        newsFeedInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'newsFeed.label', default: 'NewsFeed'), newsFeedInstance.id])
                redirect action: 'index', params: [gameAlias: params.gameAlias]
            }
            '*' { respond newsFeedInstance, [status: CREATED] }
        }
    }

    def edit(NewsFeed newsFeedInstance) {
        respond newsFeedInstance
    }

    @Transactional
    def update(NewsFeed newsFeedInstance) {
        if (newsFeedInstance == null) {
            notFound()
            return
        }

        if (newsFeedInstance.hasErrors()) {
            respond newsFeedInstance.errors, view:'edit'
            return
        }

        newsFeedInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NewsFeed.label', default: 'NewsFeed'), newsFeedInstance.id])
                redirect newsFeedInstance
            }
            '*'{ respond newsFeedInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(NewsFeed newsFeedInstance) {

        if (newsFeedInstance == null) {
            notFound()
            return
        }

        newsFeedInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NewsFeed.label', default: 'NewsFeed'), newsFeedInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'newsFeed.label', default: 'NewsFeed'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
