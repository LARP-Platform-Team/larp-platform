<%@ page import="ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${characterRequestInstanceList as List<CharacterRequest>}"/>
  <g:set var="title" value="Заявки на участие в игре"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" mapping="game"><i class="arrow left grey icon"></i> Назад</ingame:link>
  <ingame:link mapping="gameRequest" class="item"
               controller="characterRequestField" action="index">
    <i class="options icon"></i> Настройка анкеты</ingame:link>
</content>

<content tag="content">

  <g:if test="${params.all}">
    <ingame:link mapping="gameRequest" class="ui blue basic button"
                 controller="characterRequest" action="index">
      Показать только активные</ingame:link>
  </g:if>
  <g:else>
    <ingame:link mapping="gameRequest" class="ui blue button" params="[all: true]"
                 controller="characterRequest" action="index">
      Показать все</ingame:link>
  </g:else>

  <table class="ui celled padded very basic fixed table">
    <thead>
    <tr>
      <th>Статус</th>
      <th>Заявка</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${subject}" var="item">
      <tr>
        <td>
          <g:render template="status" model="[status: item.status]"/>
        </td>
        <td>
          Заявка игрока ${item.user.name} от <g:render template="/shared/date" model="[date: item.creationDate]"/>
        </td>
        <td>
          <ingame:link mapping="gameRequest" class="ui blue compact basic icon button"
                       controller="characterRequest" title="Смотреть"
                       action="show" id="${item.id}"><i
              class="blue unhide icon"></i></ingame:link>
        </td>
      </tr>
    </g:each>
    </tbody>
    <g:render template="/shared/semantic/tablePaginate"
              model="[colspan: 3, itemsQty: itemsQty]"/>
  </table>
</content>

</body>
</html>