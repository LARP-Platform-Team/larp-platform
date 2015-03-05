<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<div class="game-role">
    <div class="title">
        ${role.title} (${role.characters.size()} персонажей)
        <sec:permitted object="${params.game}" permission="administration">
            &nbsp;<ingame:link action="create" params="[parent: role.id]">[+]</ingame:link>
            &nbsp;<ingame:link action="edit" id="${role.id}">[e]</ingame:link>
            &nbsp;<ingame:link action="delete" id="${role.id}">[x]</ingame:link>
        </sec:permitted>
    </div>
    <g:if test="${role.subRoles}">
        <g:render template="show" collection="${role.subRoles}" var="role"/>
    </g:if>
</div>