package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.NewsService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class NewsItemController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST"]
    NewsService newsService

    def create() {
        if(!params.feedId || !NewsFeed.exists(params.feedId)) {
            response.sendError(NOT_FOUND.value())
            return
        }
        respond newsService.createNews(NewsFeed.get(params.feedId))
    }

    def edit(NewsItem newsItem) {
        respond newsService.editNews(newsItem)
    }

    @Transactional
    def save(NewsItem newsItem) {
        if (validateData(newsItem, 'create')) {
            newsService.saveNews(newsItem)
            respondChange('default.created.message', CREATED, newsItem)
        }
    }

    @Transactional
    def update(NewsItem newsItem) {
        if (validateData(newsItem, 'edit')) {
            newsService.updateNews(newsItem)
            respondChange('default.updated.message', OK, newsItem)
        }
    }

    @Transactional
    def delete(NewsItem newsItem) {
        if(validateData(newsItem)) {
            newsService.deleteNews(newsItem)
            // save feed id to params for redirect
            params.feed = [:]
            params.feed.id = newsItem.feed.id
            respondChange('default.deleted.message', NO_CONTENT, null, newsItem.id)
        }
    }

    @Override
    protected Map redirectParams() {
        def attrs = super.redirectParams()
        attrs.controller = 'NewsFeed'
        attrs.action = 'show'
        attrs.id = params.feed.id
        return attrs
    }

    @Override
    protected String labelCode() { return 'newsItem.label' }
}
