
<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'gameRole.label', default: 'GameRole')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-gameRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-gameRole" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list gameRole">
			
				<g:if test="${gameRoleInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="gameRole.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="gameRole" action="show" id="${gameRoleInstance?.parent?.id}">${gameRoleInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameRoleInstance?.game}">
				<li class="fieldcontain">
					<span id="game-label" class="property-label"><g:message code="gameRole.game.label" default="Game" /></span>
					
						<span class="property-value" aria-labelledby="game-label"><g:link controller="game" action="show" id="${gameRoleInstance?.game?.id}">${gameRoleInstance?.game?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${gameRoleInstance?.subRoles}">
				<li class="fieldcontain">
					<span id="subRoles-label" class="property-label"><g:message code="gameRole.subRoles.label" default="Sub Roles" /></span>
					
						<g:each in="${gameRoleInstance.subRoles}" var="s">
						<span class="property-value" aria-labelledby="subRoles-label"><g:link controller="gameRole" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${gameRoleInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="gameRole.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${gameRoleInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:gameRoleInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${gameRoleInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
