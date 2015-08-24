<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>

<article class="item">
  <div class="content">
    <h2 class="header">${item.title}</h2>

    <div class="meta">
      <g:render template="/shared/date" model="[date: item.created]"/>
    </div>

    <div class="description">${item.text}</div>

    <div class="extra">
      <div class="ui buttons">
        <sec:permitted object="${item.feed}" permission="write">
          <ingame:link controller="NewsItem" action="edit" id="${item.id}" title="Править"
                       class="ui basic compact yellow icon button"><i
              class="yellow icon edit"></i></ingame:link>
        </sec:permitted>
        <sec:permitted object="${item.feed}" permission="delete">
          <ingame:link controller="NewsItem" action="delete" id="${item.id}" title="Удалить"
                       class="ui basic compact red icon button"
                       onclick="return confirm('Вы уверены?');">
            <i class="red delete icon"></i></ingame:link>
        </sec:permitted>
      </div>
    </div>
  </div>
</article>
