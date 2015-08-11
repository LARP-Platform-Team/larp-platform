<div class="ui middle aligned horizontal relaxed divided selection list">
  <g:if test="${!items}">
    <br/><ui:message type="info">Пока не добавлено ни одного источника.</ui:message>
  </g:if>
  <g:each in="${items}" var="item">
    <div class="item">
      <div class="ui right floated buttons content">
        <ingame:remoteLink class="ui compact icon red basic button" title="Удалить"
                           url="[action: 'deleteOrigin', id: item.id]"
                           update="[success: 'itemsContainer', failure: 'addOriginError']">
          <i class="red delete icon"></i>
        </ingame:remoteLink>
      </div>
      <div class="content">
        <div class="header">${item.title}</div>
      </div>
    </div>
  </g:each>

</div>

<div id="addOriginError"></div>
<div class="ui divider"></div>
