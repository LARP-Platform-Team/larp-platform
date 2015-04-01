<span id="gameMasters">
    <g:each in="${masters}" var="master">
        <span>${master.username} [<ingame:remoteLink
                url="[action: 'removeMaster', id: params.id, params: [masterId: master.id]]"
                update="[success: 'gameMasters', failure: 'gameMastersError']">x</ingame:remoteLink>] |</span>
    </g:each>
    <div class="errors" id="gameMastersError"></div>
</span>