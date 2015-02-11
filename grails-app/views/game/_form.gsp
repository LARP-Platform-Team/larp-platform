<%@ page import="ru.srms.larp.platform.game.Game" %>



<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'alias', 'error')} required">
    <label for="alias">
        <g:message code="game.alias.label" default="Alias"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="alias" pattern="${gameInstance.constraints.alias.matches}" required=""
                 value="${gameInstance?.alias}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'overview', 'error')} required">
    <label for="overview">
        <g:message code="game.overview.label" default="Overview"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textArea name="overview" cols="40" rows="5" maxlength="9999" required=""
                value="${gameInstance?.overview}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'characters', 'error')} ">
    <label for="characters">
        <g:message code="game.characters.label" default="Characters"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${gameInstance?.characters ?}" var="c">
            <li><g:link controller="gameCharacter" action="show"
                        id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="gameCharacter" action="create"
                    params="['game.id': gameInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'gameCharacter.label', default: 'GameCharacter')])}</g:link>
        </li>
    </ul>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'masters', 'error')} ">
    <label for="masters">
        <g:message code="game.masters.label" default="Masters"/>

    </label>
    <g:select name="masters" from="${ru.srms.larp.platform.sec.SpringUser.list()}"
              multiple="multiple" optionKey="id" size="5" value="${gameInstance?.masters*.id}"
              class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="game.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${gameInstance?.title}"/>

</div>

