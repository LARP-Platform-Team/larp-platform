package ru.srms.larp.platform.game.news

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.NewsService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class NewsItemController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]
  NewsService newsService

  def create() {
    withModule {
      if (!params.feedId || !NewsFeed.exists(params.feedId)) {
        response.sendError(NOT_FOUND.value())
        return
      }
      respond newsService.createNews(NewsFeed.get(params.feedId))
    }
  }

  def edit(NewsItem newsItem) {
    withModule {
      respond newsService.editNews(newsItem)
    }
  }

  @Transactional
  def save(NewsItem newsItem) {
    withModule {
      if (validateData(newsItem, 'create')) {
        newsService.saveNews(newsItem)
        respondChange('Новость успешно добавлена', CREATED, newsItem)
      }
    }
  }

  @Transactional
  def update(NewsItem newsItem) {
    withModule {
      if (validateData(newsItem, 'edit')) {
        newsService.updateNews(newsItem)
        respondChange('Новость обновлена', OK, newsItem)
      }
    }
  }

  @Transactional
  def delete(NewsItem newsItem) {
    withModule {
      if (validateData(newsItem)) {
        newsService.deleteNews(newsItem)
        // save feed id to params for redirect
        params.feed = [:]
        params.feed.id = newsItem.feed.id
        respondChange('Новость удалена', NO_CONTENT, null, newsItem.id)
      }
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
  protected Game.GameModule module() {
    return Game.GameModule.NEWS
  }
}
