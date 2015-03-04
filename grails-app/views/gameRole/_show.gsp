<div class="game-role">
    <div class="title">${role.title}</div>
    <g:if test="${role.subRoles}">
        <g:render template="show" collection="${role.subRoles}" var="role"/>
    </g:if>
</div>