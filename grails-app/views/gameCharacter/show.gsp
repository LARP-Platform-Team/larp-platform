<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName"
           value="${message(code: 'gameCharacter.label', default: 'GameCharacter')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-gameCharacter" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message
                code="default.home.label"/></a></li>
    </ul>
</div>

<div id="show-gameCharacter" class="content scaffold-show" role="main">
    <h1>${gameCharacterInstance?.name}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list gameCharacter">
        <g:if test="${gameCharacterInstance?.player}">
            <li class="fieldcontain">
                <span id="player-label" class="property-label"><g:message
                        code="gameCharacter.player.label" default="Player"/></span>

                <span class="property-value" aria-labelledby="player-label"><g:link
                        controller="springUser" action="show"
                        id="${gameCharacterInstance?.player?.id}">${gameCharacterInstance?.player?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${gameCharacterInstance?.isDead}">
            <li class="fieldcontain">
                <span id="isDead-label" class="property-label"><g:message
                        code="gameCharacter.isDead.label" default="Is Dead"/></span>

                <span class="property-value" aria-labelledby="isDead-label"><g:formatBoolean
                        boolean="${gameCharacterInstance?.isDead}"/></span>

            </li>
        </g:if>
    </ol>
</div>
</body>
</html>
