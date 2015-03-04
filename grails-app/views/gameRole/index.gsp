
<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'gameRole.label', default: 'GameRole')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-gameRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><ingame:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></ingame:link></li>
			</ul>
		</div>
		<div id="list-gameRole" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
            <div class="property-list">
                <g:render template="show" collection="${gameRoleInstanceList}" var="role"/>
            </div>
			<div class="pagination">
				<ingame:paginate total="${rolesCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
