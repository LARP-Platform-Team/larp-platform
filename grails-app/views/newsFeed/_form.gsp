<%@ page import="ru.srms.larp.platform.game.Game; ru.srms.larp.platform.game.news.NewsFeed" %>

<div class="${hasErrors(bean: newsFeedInstance, field: 'title', 'error')} required eight wide field">
  <label for="title">Название:</label>
  <g:textField name="title" required="" value="${newsFeedInstance?.title}"/>
  <div class="ui pointing label">Максимум 64 символа. Должно быть уникальным в рамках игры.</div>
</div>