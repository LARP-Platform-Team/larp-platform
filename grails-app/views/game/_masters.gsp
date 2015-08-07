<div class="ui middle aligned selection list">
    <g:each in="${masters}" var="master">
        <div class="item">
            <div class="right floated content">
                <ingame:remoteLink class="ui icon basic button"
                                   url="[action: 'removeMaster', id: params.id, params: [masterId: master.id]]"
                                   update="[success: 'gameMasters', failure: 'gameMastersError']">
                    <i class="red delete icon"></i>
                </ingame:remoteLink>
            </div>
            <img class="ui avatar image" src="${assetPath(src: 'dummy.png')}">

            <div class="content">
                <div class="header">${master.username}</div>
            </div>
        </div>
    </g:each>
    <div id="gameMastersError"></div>
</div>