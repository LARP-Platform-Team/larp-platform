<%@ page import="ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Почтовые ящики"/>
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

<div id="list-mailBox" class="content scaffold-list" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
    <tr>
      <g:sortableColumn property="address" title="Адрес"/>
      <g:sortableColumn property="owner" title="Владелец"/>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${mailBoxInstanceList}" status="i" var="box">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
        <td><ingame:link action="show"
                         id="${box.id}">${box.toString()}</ingame:link></td>
        <td>${box.owner}</td>
        <td class="buttons">
          <ingame:link class="edit" action="edit" resource="${box}">Редактировать</ingame:link>
          <ingame:link class="delete" action="delete" resource="${box}">Удалить</ingame:link>
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
