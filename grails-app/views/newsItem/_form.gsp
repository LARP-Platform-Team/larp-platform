<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>


<div class="${hasErrors(bean: newsItemInstance, field: 'title', 'error')} required field">
  <label for="title">Заголовок</label>
  <g:textField name="title" required="" value="${newsItemInstance?.title}"/>
</div>

<div class="${hasErrors(bean: newsItemInstance, field: 'created', 'error')} required inline field">
  <label for="created">Дата и время</label>
  <g:datePicker name="created" precision="minute" value="${newsItemInstance?.created}"/>

</div>

<div class="${hasErrors(bean: newsItemInstance, field: 'text', 'error')} required field">
  <label for="text">Текст</label>
  <g:textArea name="text" cols="40" rows="5" maxlength="9999" required="" class="rich"
              value="${newsItemInstance?.text}"/>

</div>

<g:hiddenField id="feed" name="feed.id" value="${newsItemInstance.feed.id}"/>