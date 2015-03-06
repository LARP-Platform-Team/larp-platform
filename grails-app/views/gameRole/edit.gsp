<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; org.springframework.validation.FieldError; ru.srms.larp.platform.game.roles.GameRole" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'gameRole.label', default: 'GameRole')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-gameRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

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

<div id="edit-gameRole" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${gameRoleInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${gameRoleInstance}" var="error">
                <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <ingame:form url="[resource: gameRoleInstance, action: 'update']" method="POST">
        <g:hiddenField name="version" value="${gameRoleInstance?.version}"/>
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </ingame:form>

    <ingame:formRemote name="characters"
                       url="[resource: gameRoleInstance, action: 'addToChar']"
                       update="[success: 'role-characters', failure: 'addCharError']" method="POST">

        <fieldset class="form">
            <div class="fieldcontain">
                <label for="characters">
                    <g:message code="characters.label" default="Персонажи"/>
                </label>

                <g:render template="characters" model="[characters: gameRoleInstance.characters]"/>
            </div>

            <div class="fieldcontain">
                <label for="characters">Выбрать персонажа</label>
                <g:select name="character.id" from="${GameCharacter.findAllByGame(params.game)}"
                optionKey="id" />
            </div>

        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="addCharacter" value="Назначить"/>
        </fieldset>
    </ingame:formRemote>





</div>
</body>
</html>
