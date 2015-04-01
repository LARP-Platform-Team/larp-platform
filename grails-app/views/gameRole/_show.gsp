<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<div class="game-role">
    <div class="title">
        ${role.title} (${role.characters.size()} персонажей)
        <sec:permitted object="${params.game}" permission="administration">
            &nbsp;<ingame:link action="create" title="Создать дочернюю роль" params="[parent: role.id]">[+]</ingame:link>
            &nbsp;<ingame:link action="edit" title="Редактировать и назначить" id="${role.id}">[e]</ingame:link>
            &nbsp;<ingame:link action="config" title="Задать права" id="${role.id}">[p]</ingame:link>
            &nbsp;<ingame:link action="delete" title="Удалить" id="${role.id}">[x]</ingame:link>
        </sec:permitted>
    </div>
    <g:if test="${role.subRoles}">
        <g:render template="show" collection="${role.subRoles}" var="role"/>
    </g:if>
</div>