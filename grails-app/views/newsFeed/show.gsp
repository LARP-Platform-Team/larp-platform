<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="feed" value="${newsFeedInstance as NewsFeed}"/>
    <title>${feed.title}</title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>

        <sec:permitted object="${feed}" permission="create">
        <li><ingame:link class="create" controller="newsItem" action="create" params="[feedId: feed.id]">
            Добавить новость
        </ingame:link></li>
        </sec:permitted>
    </ul>
</div>

<div id="show-newsFeed" class="content scaffold-show" role="main">
    <h1>${feed.title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div class="property-list newsFeed">
        <g:render template="/newsItem/show" collection="${newsItems}" var="item"/>
    </div>

    <div class="pagination">
        <ingame:paginate action="show" id="${feed.id}" total="${newsItemsCount}"/>
    </div>
</div>
</body>
</html>
