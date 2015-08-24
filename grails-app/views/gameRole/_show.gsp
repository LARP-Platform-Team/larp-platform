<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<div class="item">

  <sec:permitted object="${params.game}" permission="administration">
    <div class="right floated content">
      <div class="ui four compact buttons">
        <ingame:link action="create" title="Создать дочернюю роль"
                     class="ui green basic icon button"
                     params="[parent: role.id]"><i class="green plus icon"></i></ingame:link>
        <ingame:link action="edit" title="Редактировать и назначить"
                     class="ui yellow basic icon button"
                     id="${role.id}"><i class="yellow edit icon"></i></ingame:link>
        <ingame:link action="config" title="Задать права" class="ui blue icon basic button"
                     id="${role.id}"><i class="blue unlock alternate icon"></i></ingame:link>
        <ingame:link action="delete" title="Удалить" id="${role.id}"
                     class="ui red basic icon button">
          <i class="red delete icon"></i></ingame:link>
      </div>
    </div>
  </sec:permitted>

  <i class="large minus icon"></i>

  <div class="content">
    <div class="header">
      ${role.title}
    </div>

    <div class="description">
      Назначено персонажей: ${role.characters.size()}
    </div>
  </div>


  <g:if test="${role.subRoles}">
    <div class="ui selection animated list">
      <g:render template="show" collection="${role.subRoles}" var="role"/>
    </div>
  </g:if>
</div>