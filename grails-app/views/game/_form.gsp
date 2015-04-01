<%@ page import="ru.srms.larp.platform.game.Game" %>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'title', 'error')} required">
    <label for="title">Название<span class="required-indicator">*</span></label>
    <g:textField name="title" required="" value="${gameInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'alias', 'error')} required">
    <label for="alias">Алиас<span class="required-indicator">*</span></label>
    <g:textField name="alias" pattern="${gameInstance.constraints.alias.matches}" required=""
                 value="${gameInstance?.alias}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'overview', 'error')} required">
    <label for="overview">Описание<span class="required-indicator">*</span></label>
    <g:textArea name="overview" cols="40" rows="5" maxlength="9999" required=""
                value="${gameInstance?.overview}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'characters', 'error')} ">
    <label for="characters">Персонажи</label>

    <ul class="one-to-many">
        <g:each in="${gameInstance?.characters ?}" var="c">
            <li>${c?.encodeAsHTML()}</li>
        </g:each>
        <li class="add">
            <a href="">Управление персонажами (todo)</a>
        </li>
    </ul>

</div>