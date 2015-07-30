<span id="resource-origins">
    <g:each in="${origins}" var="origin">
        <span>${origin.title} [<ingame:remoteLink
                url="[action: 'deleteOrigin', id: origin.id]"
                update="[success: 'resource-origins', failure: 'addOriginError']">x</ingame:remoteLink>] |</span>
    </g:each>
    <div class="errors" id="addOriginError"></div>
</span>