<g:set var="icon" value="${
  type == 'error' ? 'delete' :
    type == 'warning' ? 'warning' :
      type == 'success' ? 'checkmark' :
          type == 'info' ? 'info' : null
}"/>

<div class="ui visible message ${type} ${icon ? 'icon' : ''}">

  <g:if test="${icon}">
    <i class="${icon} icon"></i>
  </g:if>

  <g:if test="${closable}">
    <i class="close icon"></i>
  </g:if>

  <div class="content">
    <g:if test="${title}">
      <div class="header">${title}</div>
    </g:if>
    <g:if test="${messageText}">
      ${raw(messageText)}
    </g:if>
    <g:else>
      ${raw(body())}
    </g:else>
  </div>
</div>