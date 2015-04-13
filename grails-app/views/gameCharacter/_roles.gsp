<span id="characterRoles">
    <g:each in="${roles}" var="role">
        <span>${role.title} [<ingame:remoteLink
                url="[action: 'removeRole', id: params.id, params: [roleId: role.id]]"
                update="[success: 'characterRoles', failure: 'addRoleError']">x</ingame:remoteLink>] |</span>
    </g:each>
    <div class="errors" id="addRoleError"></div>
</span>