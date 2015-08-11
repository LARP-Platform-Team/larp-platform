<%@ page import="ru.srms.larp.platform.game.Game; ru.srms.larp.platform.game.news.NewsFeed" %>

<div class="${hasErrors(bean: newsFeedInstance, field: 'title', 'error')} required eight wide field">
    <label for="title">Название:</label>
    <g:textField name="title" required="" value="${newsFeedInstance?.title}"/>
</div>