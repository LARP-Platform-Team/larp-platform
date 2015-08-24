<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${newsFeedInstance as NewsFeed}"/>
  <g:set var="title" value="${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <g:link mapping="playAs" params="[gameAlias: params.gameAlias, charAlias: params.charAlias]"
          class="item"><i
      class="arrow left grey icon"></i> Назад</g:link>

  <sec:permitted object="${subject}" permission="create">
    <ingame:link class="item" controller="newsItem" action="create" params="[feedId: subject.id]">
      <i class="add green icon"></i> Добавить новость
    </ingame:link>
  </sec:permitted>
</content>
<content tag="content">
  <div class="ui divided items">
    <g:render template="/newsItem/show" collection="${newsItems}" var="item"/>
  </div>

  <div class="ui pagination menu">
    <ingame:semanticPaginate id="${subject.id}" total="${newsItemsCount ?: 0}"/>
  </div>
</content>

</body>
</html>
