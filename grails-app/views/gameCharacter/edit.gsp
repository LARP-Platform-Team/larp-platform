<%@ page import="org.springframework.validation.FieldError; ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="title" value="Редактировать персонажа"/>
    <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
        <li><ingame:link class="list" action="index"><g:message code="default.list.label"
                                                                args="[entityName]"/></ingame:link></li>
        <li><ingame:link class="create" action="create"><g:message code="default.new.label"
                                                                   args="[entityName]"/></ingame:link></li>
    </ul>
</div>

<div id="edit-newsFeed" class="content scaffold-edit" role="main">
    <h1>${title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${gameCharacterInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${gameCharacterInstance}" var="error">
                <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <ingame:form url="[resource: gameCharacterInstance, action: 'update']">
        <g:hiddenField name="version" value="${gameCharacterInstance?.version}"/>
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </ingame:form>
</div>
</body>
</html>
