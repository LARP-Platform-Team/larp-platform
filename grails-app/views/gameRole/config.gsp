<%@ page import="ru.srms.larp.platform.sec.permissions.AclConfigModel; ru.srms.larp.platform.game.character.GameCharacter; org.springframework.validation.FieldError; ru.srms.larp.platform.game.roles.GameRole" %>
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
    <h1>Настройка <g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:each in="${acls}" var="aclE">
        <div>
            ${aclE.id} -- ${aclE.title} ::
                <g:each in="${AclConfigModel.GamePermission.values()}" var="perm">
                ${perm.toString()}=${aclE.permissions.contains(perm)}
            </g:each>
        </div>
    </g:each>


</div>
</body>
</html>
