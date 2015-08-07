<g:if test="${flash.error}">
  <g:render template="/shared/semantic/message" model="[type: 'error', title: 'Ошибка!']">
    <p>${flash.error}</p>
  </g:render>
</g:if>

<g:if test="${flash.warning}">
  <g:render template="/shared/semantic/message" model="[type: 'warning', title: 'Внимание!']">
    <p>${flash.warning}</p>
  </g:render>
</g:if>
<g:if test="${flash.message}">
  <g:render template="/shared/semantic/message" model="[type: 'info']">
    <p>${flash.message}</p>
  </g:render>
</g:if>
<g:if test="${flash.success}">
  <g:render template="/shared/semantic/message" model="[type: 'success', title: 'Успешно!']">
    <p>${flash.success}</p>
  </g:render>
</g:if>