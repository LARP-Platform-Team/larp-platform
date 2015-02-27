<%@ page import="ru.srms.larp.platform.game.Game; ru.srms.larp.platform.game.news.NewsFeed" %>

<g:set var="game"
       value="${newsFeedInstance?.game ?: Game.findByAlias(params.gameAlias)}"/>

<div class="fieldcontain ${hasErrors(bean: newsFeedInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="newsFeed.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${newsFeedInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: newsFeedInstance, field: 'game', 'error')} required">
    <label for="game">
        <g:message code="newsFeed.game.label" default="Game"/>
        <span class="required-indicator">*</span>
    </label>
    <span>${game.title}</span>
    <g:hiddenField name="game.id" id="game" value="${game?.id}"/>
</div>