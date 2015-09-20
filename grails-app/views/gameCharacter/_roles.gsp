<g:if test="${!items}">
    <br/><ui:message type="info">Персонажу не назначено ни одной роли.</ui:message>
</g:if>
<g:each in="${items}" var="item">
    <div class="item">
        <div class="ui right floated buttons content">
            <ingame:link controller="GameRole" action="edit" title="Редактировать" id="${item.id}"
                         class="ui compact icon yellow basic button"><i
                    class="yellow edit icon"></i></ingame:link>
            <ingame:link controller="GameRole" action="config" title="Настроить права"
                         id="${item.id}"
                         class="ui compact icon orange basic button"><i
                    class="orange options icon"></i></ingame:link>
            <ingame:remoteLink class="ui compact icon red basic button larp-ajax-self-delete" title="Удалить"
                               url="[action: 'removeRole', id: params.id, params: [roleId: item.id]]"
                               update="[success: 'rolesContainer', failure: 'addRoleError']">
                <i class="red delete icon"></i>
            </ingame:remoteLink>
        </div>

        <div class="content">
            <div class="header">${item.title}</div>
            <g:if test="${item.parent}">
                <p>Родительская роль: ${item.parent.title}</p>
            </g:if>
        </div>
    </div>
</g:each>