package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.http.HttpStatus
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.NewsService

import static org.springframework.http.HttpStatus.*

@Secured(['permitAll'])
// TODO надо ли делать контроллер Transactional, если у нас таков сервис?
@Transactional(readOnly = true)
class NewsFeedController extends BaseController {

    NewsService newsService

    static allowedMethods = [save: "POST", update: "POST"]

    def index(Integer max) {
        render(view: 'index', model: [feeds: newsService.listAdminFeeds(params.game)])
        // TODO pagination
//        params.max = Math.min(max ?: 10, 100)
//        respond NewsFeed.list(params), model:[newsFeedInstanceCount: NewsFeed.count()]
    }

    def show(NewsFeed feed) {
        respond newsService.readFeed(feed)
    }

    def create() {
        respond newsService.createFeed(params.game)
    }

    def edit(NewsFeed feed) {
        respond newsService.editFeed(feed)
    }

    @Transactional
    def save(NewsFeed feed) {
        if (saveOrUpdate(feed, 'create'))
            respondChange('default.created.message', CREATED, feed)
    }

    @Transactional
    def update(NewsFeed feed) {
        if (saveOrUpdate(feed, 'edit'))
            respondChange('default.updated.message', OK, feed)
    }

    @Transactional
    def delete(NewsFeed feed) {
        if (feed == null) {
            notFound()
            return
        }
        newsService.deleteFeed(feed)
        respondChange('default.deleted.message', NO_CONTENT, null, feed.id)
    }

    // TODO maybe pull up
    @Transactional
    protected boolean saveOrUpdate(NewsFeed feed, String currentView) {
        if (feed == null) {
            notFound()
            return false
        }

        if (feed.hasErrors()) {
            respond feed.errors, view: currentView
            return false
        }

        newsService.saveFeed(feed)
        return true
    }

    protected respondChange(String messageCode, HttpStatus respondStatus, NewsFeed subject, Object id = null) {
        return super.respondChange(messageCode, 'newsFeed.label', respondStatus, subject, id)
    }
}
