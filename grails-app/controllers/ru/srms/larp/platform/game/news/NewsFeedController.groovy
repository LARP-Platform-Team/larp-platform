package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
// TODO надо ли делать контроллер Transactional, если у нас таков сервис?
@Transactional(readOnly = true)
class NewsFeedController extends BaseModuleController {

  NewsService newsService

  static allowedMethods = [save: "POST", update: "POST"]

  def index() {
    withModule
        {
          render(view: 'index', model: [
              feeds     : newsService.listAdminFeeds(params.game, paginator()),
              feedsCount: newsService.countAdminFeeds(params.game)
          ])
        }
  }

  def show(NewsFeed feed) {
    withModule {
      respond newsService.readFeed(feed), model: [
          newsItems     : NewsItem.findAllByFeed(feed, paginator()),
          newsItemsCount: NewsItem.countByFeed(feed)]
    }
  }

  def create() {
    withModule {
      respond newsService.createFeed(params.game)
    }
  }

  def edit(NewsFeed feed) {
    withModule {
      respond newsService.editFeed(feed)
    }
  }

  @Transactional
  def save(NewsFeed feed) {
    withModule {
      if (validateData(feed, 'create')) {
        newsService.saveFeed(feed)
        respondChange('Новостная лента успешно создана', CREATED)
      }
    }
  }

  @Transactional
  def update(NewsFeed feed) {
    withModule {
      if (validateData(feed, 'edit')) {
        newsService.saveFeed(feed)
        respondChange('Новостная лента успешно обновлена', OK)
      }
    }
  }

  @Transactional
  def delete(NewsFeed feed) {
    withModule {
      if (validateData(feed)) {
        newsService.deleteFeed(feed)
        respondChange('Новостная лента удалена', NO_CONTENT)
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.NEWS
  }
}
