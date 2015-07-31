<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="character">
    <g:set var="entityName"
           value="${message(code: 'gameCharacter.label', default: 'GameCharacter')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
    </ul>
</div>

<div id="show-gameCharacter" class="content scaffold-show" role="main">
    <h1>${character.name}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list gameCharacter">
        <g:if test="${character.player}">
            <li class="fieldcontain">
                <span id="player-label" class="property-label">Игрок:</span>
                <span class="property-value" aria-labelledby="player-label"><g:link
                        controller="springUser" action="show"
                        id="${character.player?.id}">${character.player?.username}</g:link></span>
            </li>
        </g:if>

        <li class="fieldcontain">
            <span id="isDead-label" class="property-label">Живой:</span>
            <span class="property-value" aria-labelledby="isDead-label"><g:formatBoolean
                    boolean="${!character.isDead}"/></span>
        </li>

        <li class="fieldcontain">
            <span id="roles-label" class="property-label">Роли</span>
            <span class="property-value">
                <g:each in="${character.roles}" var="role">
                    <div>${role.title}</div>
                </g:each>
            </span>
        </li>
    </ol>

</div>
</body>
</html>
