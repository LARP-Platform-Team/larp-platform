package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.NewsService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
// TODO надо ли делать контроллер Transactional, если у нас таков сервис?
@Transactional(readOnly = true)
class NewsFeedController extends BaseController {

    NewsService newsService

    static allowedMethods = [save: "POST", update: "POST"]

    def index() {
        render(view: 'index', model: [
                feeds: newsService.listAdminFeeds(params.game, paginator()),
                feedsCount: newsService.countAdminFeeds(params.game)
        ])
    }

    def show(NewsFeed feed) {
        respond newsService.readFeed(feed), model: [
                newsItems: NewsItem.findAllByFeed(feed, paginator()),
                newsItemsCount: NewsItem.countByFeed(feed)]
    }

    def create() {
        respond newsService.createFeed(params.game)
    }

    def edit(NewsFeed feed) {
        respond newsService.editFeed(feed)
    }

    @Transactional
    def save(NewsFeed feed) {
        if (validateData(feed, 'create')) {
            newsService.saveFeed(feed)
            respondChange('default.created.message', CREATED, feed)
        }
    }

    @Transactional
    def update(NewsFeed feed) {
        if (validateData(feed, 'edit')) {
            newsService.saveFeed(feed)
            respondChange('default.updated.message', OK, feed)
        }
    }

    @Transactional
    def delete(NewsFeed feed) {
        if(validateData(feed)) {
            newsService.deleteFeed(feed)
            respondChange('default.deleted.message', NO_CONTENT, null, feed.id)
        }
    }

    @Override
    protected String labelCode() { 'newsFeed.label' }
}
