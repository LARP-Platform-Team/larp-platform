<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="title" value="${params.game.title} - Игровые персонажи" />
		<title>${title}</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><ingame:link class="create" action="create">Добавить</ingame:link></li>
			</ul>
		</div>
		<div id="list-newsFeed" class="content scaffold-list" role="main">
			<h1>${title}</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="title" title="Имя" />
						<th>Игрок</th>
                        <th>Действия</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${characters}" status="i" var="item">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><ingame:link action="show" id="${item.id}">${item.name}</ingame:link></td>
						<td>${item.player}</td>
					    <td class="buttons">
                            <ingame:link action="edit" id="${item.id}" class="edit">${message(code: 'default.button.edit.label')}</ingame:link>
                            <ingame:link class="delete" action="delete" id="${item.id}"
                                        onclick="return confirm('Are you sure?');">${message(code: 'default.button.delete.label')}</ingame:link>
                        </td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<ingame:paginate total="${itemsCount}" />
			</div>
		</div>
	</body>
</html>
