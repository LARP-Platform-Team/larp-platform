<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>



<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="gameRole.parent.label" default="Parent" />
		
	</label>
	<g:select id="parent" name="parent.id" from="${ru.srms.larp.platform.game.roles.GameRole.list()}" optionKey="id" value="${gameRoleInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'game', 'error')} required">
	<label for="game">
		<g:message code="gameRole.game.label" default="Game" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="game" name="game.id" from="${ru.srms.larp.platform.game.Game.list()}" optionKey="id" required="" value="${gameRoleInstance?.game?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'subRoles', 'error')} ">
	<label for="subRoles">
		<g:message code="gameRole.subRoles.label" default="Sub Roles" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${gameRoleInstance?.subRoles?}" var="s">
    <li><g:link controller="gameRole" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="gameRole" action="create" params="['gameRole.id': gameRoleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'gameRole.label', default: 'GameRole')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="gameRole.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${gameRoleInstance?.title}"/>

</div>

