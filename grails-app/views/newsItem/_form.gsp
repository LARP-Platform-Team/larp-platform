<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>



<div class="fieldcontain ${hasErrors(bean: newsItemInstance, field: 'text', 'error')} required">
	<label for="text">
		<g:message code="newsItem.text.label" default="Text" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="text" cols="40" rows="5" maxlength="9999" required="" value="${newsItemInstance?.text}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: newsItemInstance, field: 'created', 'error')} required">
	<label for="created">
		<g:message code="newsItem.created.label" default="Created" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="created" precision="day"  value="${newsItemInstance?.created}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: newsItemInstance, field: 'feed', 'error')} required">
	<label for="feed">
		<g:message code="newsItem.feed.label" default="Feed" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="feed" name="feed.id" from="${ru.srms.larp.platform.game.news.NewsFeed.list()}" optionKey="id" required="" value="${newsItemInstance?.feed?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: newsItemInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="newsItem.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${newsItemInstance?.title}"/>

</div>

