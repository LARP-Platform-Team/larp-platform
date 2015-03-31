<%@ page import="ru.srms.larp.platform.sec.permissions.GamePermission" %>
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

    <table>
        <thead>
        <tr>
            <th>Элемент</th>
            <g:each in="${GamePermission.values()}" var="perm">
                <th>${perm.toString()}</th>
            </g:each>
        </tr>
        </thead>
        <tbody>
        <g:each in="${acls}" var="aclItem">
            <tr>
                <td>${aclItem.id} -- ${aclItem.title}</td>
                <g:each in="${GamePermission.values()}" var="perm">
                    <td>
                        <ingame:remoteLink
                                elementId="permisson-cell-${aclItem.id}-${perm.toString()}"
                                url="[action: 'setPermission', id: params.id, params: [
                                        itemId: aclItem.id,
                                        permission: perm.toString()
                                ]]"
                                update="[success: 'permisson-cell-'+aclItem.id+'-'+perm.toString(), failure: 'setPermissionError']">
                        ${aclItem.permissions.contains(perm)}
                        </ingame:remoteLink>
                    </td>
                </g:each>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="errors" id="setPermissionError"></div>

</div>
</body>
</html>
