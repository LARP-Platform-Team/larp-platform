<g:each in="${masters}" var="master">
    <div class="item">
        <div class="right floated content">

    <ingame:remoteLink class="ui icon red basic button larp-ajax-self-delete" title="Удалить"
                       url="[action: deleteAction, id: params.id, params: ['user.id': master.id]]"
                       update="${update}">
                <i class="red delete icon"></i>
            </ingame:remoteLink>
        </div>
        <img class="ui avatar image" src="${assetPath(src: 'dummy.png')}">

        <div class="content">
            <div class="header">${master.name}</div>
        </div>
    </div>
</g:each>