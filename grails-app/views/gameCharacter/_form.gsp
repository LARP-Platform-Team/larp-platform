<%@ page import="ru.srms.larp.platform.sec.SpringUser; ru.srms.larp.platform.game.Game" %>

<g:set var="game" value="${gameCharacterInstance?.game ?: params.game}"/>

<div class="fieldcontain ${hasErrors(bean: gameCharacterInstance, field: 'name', 'error')} required">
    <label for="name">Имя<span class="required-indicator">*</span></label>
    <g:textField name="name" required="" value="${gameCharacterInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameCharacterInstance, field: 'alias', 'error')} required">
    <label for="name">Алиас<span class="required-indicator">*</span></label>
    <g:textField name="alias" required="" value="${gameCharacterInstance?.alias}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameCharacterInstance, field: 'player', 'error')} required">
    <label for="name">Игрок<span class="required-indicator">*</span></label>
    <g:select name="player" from="${SpringUser.list()}" value="${gameCharacterInstance?.player?.id}"
              optionKey="id" optionValue="username" noSelection="${['null': 'нет']}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameCharacterInstance, field: 'game', 'error')} required">
    <label for="game">Игра<span class="required-indicator">*</span></label>
    <span>${game.title}</span>
    <g:hiddenField name="game.id" id="game" value="${game.id}"/>
</div>