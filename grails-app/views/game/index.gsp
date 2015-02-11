<%@ page import="ru.srms.larp.platform.game.Game" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'game.label', default: 'Game')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-game" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>

        <sec:ifLoggedIn>
            <li><g:link class="create" action="create">
                <g:message code="default.new.label" args="[entityName]"/></g:link></li>
        </sec:ifLoggedIn>
    </ul>
</div>

<div id="list-game" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="title"
                              title="${message(code: 'game.title.label', default: 'Title')}"/>

            <g:sortableColumn property="overview"
                              title="${message(code: 'game.overview.label', default: 'Overview')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${gameInstanceList}" status="i" var="gameInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="play" params="[alias: gameInstance.alias]">
                    ${fieldValue(bean: gameInstance, field: "title")}</g:link></td>

                <td>${fieldValue(bean: gameInstance, field: "overview")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${gameInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
