<div class="ui middle aligned selection list">
  <g:if test="${!characters}">
    <br/><ui:message type="info">Роль еще не назначена ни одному персонажу.</ui:message>
  </g:if>
  <g:each in="${characters}" var="item">

    <div class="item">
      <div class="ui right floated buttons content">
        <ingame:remoteLink class="ui compact icon red basic button" title="Удалить"
                           url="[action: 'removeFromChar', id: params.id, params: [characterId: item.id]]"
                           update="[success: 'charactersContainer', failure: 'addCharError']">
          <i class="red delete icon"></i>
        </ingame:remoteLink>
      </div>

      <div class="content">
        <div class="header">${item.name}</div>
      </div>
    </div>
  </g:each>

  <div class="errors" id="addCharError"></div>
  <div class="ui divider"></div>
</div>
