
<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'newsItem.label', default: 'NewsItem')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-newsItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-newsItem" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list newsItem">
			
				<g:if test="${newsItemInstance?.text}">
				<li class="fieldcontain">
					<span id="text-label" class="property-label"><g:message code="newsItem.text.label" default="Text" /></span>
					
						<span class="property-value" aria-labelledby="text-label"><g:fieldValue bean="${newsItemInstance}" field="text"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsItemInstance?.created}">
				<li class="fieldcontain">
					<span id="created-label" class="property-label"><g:message code="newsItem.created.label" default="Created" /></span>
					
						<span class="property-value" aria-labelledby="created-label"><g:formatDate date="${newsItemInstance?.created}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsItemInstance?.feed}">
				<li class="fieldcontain">
					<span id="feed-label" class="property-label"><g:message code="newsItem.feed.label" default="Feed" /></span>
					
						<span class="property-value" aria-labelledby="feed-label"><g:link controller="newsFeed" action="show" id="${newsItemInstance?.feed?.id}">${newsItemInstance?.feed?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsItemInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="newsItem.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${newsItemInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:newsItemInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${newsItemInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
