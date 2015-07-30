<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Игровые ресурсы"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
    <li><ingame:link class="create" action="create">Добавить</ingame:link></li>
  </ul>
</div>

<div id="list-gameResources" class="content scaffold-list" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
    <tr>
      <g:sortableColumn property="title" title="Название"/>
      <th>Ед. изм.</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${gameResourceInstanceList}" status="i" var="res">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
        <td><ingame:link action="show"
                         id="${res.id}">${res.title}</ingame:link></td>
        <td>${res.measure}</td>
        <td class="buttons">
          <ingame:link class="edit" action="edit" resource="${res}">Редактировать</ingame:link>
          <ingame:link class="delete" action="delete" resource="${res}">Удалить</ingame:link>
        </td>
      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="pagination">
    <g:paginate total="${itemsCount ?: 0}"/>
  </div>
</div>
</body>
</html>
