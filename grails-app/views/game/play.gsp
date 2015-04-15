<%@ page import="ru.srms.larp.platform.game.Game" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'game.label', default: 'Game')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-game" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
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

<div id="show-game" class="content scaffold-show" role="main">

    <h1><g:fieldValue bean="${gameInstance}" field="title"/></h1>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <ol class="property-list game">

        <g:if test="${gameInstance?.overview}">
            <li class="fieldcontain">
                <span id="overview-label" class="property-label"><g:message
                        code="game.overview.label" default="Overview"/></span>

                <span class="property-value" aria-labelledby="overview-label"><g:fieldValue
                        bean="${gameInstance}" field="overview"/></span>

            </li>
        </g:if>

        <g:if test="${characters}">
            <li class="fieldcontain">
                <span id="characters-label" class="property-label">Вы можете играть за:</span>

                <g:each in="${characters}" var="c">
                    <span class="property-value" aria-labelledby="characters-label">
                        <link:playAs charAlias="${c.alias}" gameAlias="${gameInstance.alias}">${c.name}</link:playAs>
                    </span>
                </g:each>
            </li>
        </g:if>


        <li class="fieldcontain">
                <span id="masters-label" class="property-label"><g:message code="game.masters.label"
                                                                           default="Masters"/></span>

                <g:each in="${gameInstance.masters}" var="m">
                    <span class="property-value" aria-labelledby="masters-label"><g:link
                            controller="springUser" action="show"
                            id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>

    </ol>
        <fieldset class="buttons">
            <sec:permitted object="${gameInstance}" permission="administration">
               <tmpl:gmControls game="${gameInstance}"/>
            </sec:permitted>


            <sec:ifAllGranted roles="ROLE_ADMIN">
                <g:link class="delete" action="delete" resource="${gameInstance}"
                        onclick="return confirm('Вы уверены?');">Удалить</g:link>
            </sec:ifAllGranted>
        </fieldset>
</div>
</body>
</html>
