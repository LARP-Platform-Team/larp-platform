<%@ page import="ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<g:set var="characterRequest" value="${item as CharacterRequest}"/>

<div class="item">
  <div class="right floated content">
    <ingame:link mapping="gameRequest" class="ui blue compact basic icon button"
                 controller="characterRequest" title="Смотреть"
                 action="show" id="${characterRequest.id}"><i
        class="blue unhide icon"></i></ingame:link>

    <g:if test="${characterRequest.status.data.editable}">
      <ingame:link mapping="gameRequest" class="ui yellow compact basic icon button"
                   controller="characterRequest" title="Редактировать"
                   action="edit" id="${characterRequest.id}"><i
          class="yellow edit icon"></i></ingame:link>
    </g:if>
  </div>

  <div class="header">Заявка от <g:render template="/shared/date" model="[date: characterRequest.creationDate]"/></div>

  <div class="content">
    <g:render template="/characterRequest/status" model="[status: characterRequest.status]"/>
  </div>
</div>
