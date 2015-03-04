
<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'newsFeed.label', default: 'NewsFeed')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-newsFeed" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><ingame:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></ingame:link></li>
			</ul>
		</div>
		<div id="list-newsFeed" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="title" title="${message(code: 'property.title.label', default: 'Title')}" />
                        <th>Actions</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${feeds}" status="i" var="feed">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><ingame:link action="show" id="${feed.id}">${feed.title}</ingame:link></td>
					    <td class="buttons">
                            <ingame:link action="edit" id="${feed.id}" class="edit">${message(code: 'default.button.edit.label')}</ingame:link>
                            <ingame:link class="delete" action="delete" id="${feed.id}"
                                        onclick="return confirm('Are you sure?');">${message(code: 'default.button.delete.label')}</ingame:link>
                        </td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<ingame:paginate total="${feedsCount}" />
			</div>
		</div>
	</body>
</html>
