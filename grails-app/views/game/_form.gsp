<%@ page import="ru.srms.larp.platform.game.Game" %>



<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'characters', 'error')} ">
	<label for="characters">
		<g:message code="game.characters.label" default="Characters" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${gameInstance?.characters?}" var="c">
    <li><g:link controller="gameCharacter" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="gameCharacter" action="create" params="['game.id': gameInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'gameCharacter.label', default: 'GameCharacter')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'masters', 'error')} ">
	<label for="masters">
		<g:message code="game.masters.label" default="Masters" />
		
	</label>
	<g:select name="masters" from="${ru.srms.larp.platform.sec.SpringUser.list()}" multiple="multiple" optionKey="id" size="5" value="${gameInstance?.masters*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="game.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${gameInstance?.title}"/>

</div>

