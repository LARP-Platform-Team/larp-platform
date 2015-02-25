<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'newsFeed.label', default: 'NewsFeed')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-newsFeed" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label"
                                                           args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-newsFeed" class="content scaffold-show" role="main">
    <h1>${newsFeedInstance.title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div class="property-list newsFeed">
        <g:if test="${newsFeedInstance?.newsItems}">
            <g:each in="${newsFeedInstance.newsItems}" var="n">
                    <article style="margin-bottom: 15px">
                        <h2>${n.title}</h2>
                        <g:render template="/shared/date" model="[date: n.created]"/>
                        <p>${n.text}</p>
                    </article>
            </g:each>

        </g:if>
    </div>

    <g:form url="[resource: newsFeedInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${newsFeedInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
