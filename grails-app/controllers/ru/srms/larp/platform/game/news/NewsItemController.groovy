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
      newsItem.text = cleanHtml(newsItem.text, 'rich-text')
      if (validateData(newsItem, 'create')) {
        newsService.saveNews(newsItem)
        respondChange('Новость успешно добавлена', CREATED,
            [controller: 'NewsFeed', action: 'show', id: newsItem.feed.id])
      }
    }
  }

  @Transactional
  def update(NewsItem newsItem) {
    withModule {
      newsItem.text = cleanHtml(newsItem.text, 'rich-text')
      if (validateData(newsItem, 'edit')) {
        newsService.updateNews(newsItem)
        respondChange('Новость обновлена', OK,
            [controller: 'NewsFeed', action: 'show', id: newsItem.feed.id])
      }
    }
  }

  @Transactional
  def delete(NewsItem newsItem) {
    withModule {
      if (validateData(newsItem)) {
        newsService.deleteNews(newsItem)
        respondChange('Новость удалена', NO_CONTENT,
            [controller: 'NewsFeed', action: 'show', id: newsItem.feed.id])
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.NEWS
  }
}
