<span id="role-characters">
    <g:each in="${characters}" var="character">
        <span>${character.name} [<ingame:remoteLink
                url="[action: 'removeFromChar', id: params.id, params: [characterId: character.id]]"
                update="[success: 'role-characters', failure: 'addCharError']">x</ingame:remoteLink>] |</span>
    </g:each>
    <div class="errors" id="addCharError"></div>
</span>