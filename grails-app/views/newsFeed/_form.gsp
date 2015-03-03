<%@ page import="ru.srms.larp.platform.game.Game; ru.srms.larp.platform.game.news.NewsFeed" %>

<g:set var="game" value="${newsFeedInstance?.game ?: params.game}"/>

<div class="fieldcontain ${hasErrors(bean: newsFeedInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="property.title.label"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${newsFeedInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: newsFeedInstance, field: 'game', 'error')} required">
    <label for="game">
        <g:message code="game.label"/>
        <span class="required-indicator">*</span>
    </label>
    <span>${game.title}</span>
    <g:hiddenField name="game.id" id="game" value="${game.id}"/>
</div>