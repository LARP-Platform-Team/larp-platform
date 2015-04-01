<%@ page import="ru.srms.larp.platform.sec.SpringUser; org.springframework.validation.FieldError; ru.srms.larp.platform.game.Game" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'game.label', default: 'Game')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-game" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label"
                                                           args="[entityName]"/></g:link></li>

        <sec:ifLoggedIn>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
            </sec:ifLoggedIn>
    </ul>
</div>

<div id="edit-game" class="content scaffold-edit" role="main">
    <h1>Редактирование игры "${gameInstance.title}"</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${gameInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${gameInstance}" var="error">
                <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form url="[resource: gameInstance, action: 'update']" method="POST">
        <g:hiddenField name="version" value="${gameInstance?.version}"/>
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </g:form>

    <ingame:formRemote name="masters"
                       url="[resource: gameInstance, action: 'addMaster']"
                       update="[success: 'gameMasters', failure: 'gameMastersError']" method="POST">
        <fieldset class="form">
            <div class="fieldcontain">
                <label>Мастера</label>
                <g:render template="masters" model="[masters: gameInstance.masters]"/>
            </div>
            <div class="fieldcontain">
                <label for="masterId">Выбрать мастера</label>
                <g:select name="masterId" from="${SpringUser.list()}" optionKey="id"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="addMaster" value="Добавить"/>
        </fieldset>
    </ingame:formRemote>
</div>
</body>
</html>
