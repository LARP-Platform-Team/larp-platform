<%@ page import="ru.srms.larp.platform.game.chip.GameChip" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameChipInstanceList as List<GameChip>}"/>
  <g:set var="title" value="Игровые чипы"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" action="create"><i class="add green icon"></i> Добавить</ingame:link>
</content>

<content tag="content">

  <tmpl:filter/>

  <table class="ui compact celled very basic table">
    <thead>
    <tr>
      <th>Код</th>
      <th>Название</th>
      <th>Владелец</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${subject}" var="item">
      <tr>
        <td>${item.code}</td>
        <td>${item.title}</td>
        <td>${item.owner?.name}</td>
        <td>
          <g:if test="${item.notes}">
            <div class="ui blue icon basic html-popup button">
              <i class="blue info icon"></i>
            </div>
            <div class="ui flowing popup"><hc:cleanHtml unsafe="${item.notes}" whitelist="simple-rich-text"/></div>
          </g:if>
          <ingame:link action="edit" id="${item.id}" class="ui yellow icon basic button"
                       title="Редактировать"><i class="yellow edit icon"></i></ingame:link>
          <ingame:link action="delete" id="${item.id}" class="ui red icon basic button"
                       title="Удалить" onclick="return confirm('Вы уверены?');">
            <i class="red delete icon"></i></ingame:link>
        </td>
      </tr>
    </g:each>
    </tbody>
    <g:render template="/shared/semantic/tablePaginate"
              model="[colspan: 4, itemsQty: itemsQty]"/>
  </table>

</content>
</body>
</html>
