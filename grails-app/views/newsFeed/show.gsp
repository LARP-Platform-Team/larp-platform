<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'newsFeed.label', default: 'NewsFeed')}"/>
    <g:set var="feed" value="${newsFeedInstance as NewsFeed}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-newsFeed" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
        <li><g:gameLink class="create" controller="newsItem" action="create" params="[feedId: feed.id]">
            <g:message code="default.new.label"
                       args="[message(code: 'newsItem.label')]"/></g:gameLink></li>
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
        <g:gamePaginate action="show" id="${feed.id}" total="${newsItemsCount}"/>
    </div>
</div>
</body>
</html>
