
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
				<li><g:gameLink class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:gameLink></li>
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
						<g:sortableColumn property="title" title="${message(code: 'newsFeed.title.label', default: 'Title')}" />
                        <th>Actions</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${feeds}" status="i" var="feed">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:gameLink action="show" id="${feed.id}">${feed.title}</g:gameLink></td>
					    <td>
                            <g:gameLink action="edit" id="${feed.id}" class="edit">Edit</g:gameLink>
                            <g:gameLink class="delete" action="delete" id="${feed.id}"
                                        onclick="return confirm('Are you sure?');">Delete</g:gameLink>
                        </td>
					</tr>
				</g:each>
				</tbody>
			</table>
			%{--<div class="pagination">--}%
				%{--<g:paginate total="${feedsCount ?: 0}" />--}%
			%{--</div>--}%
		</div>
	</body>
</html>
