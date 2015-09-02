<%@ page import="ru.srms.larp.platform.game.resources.ResourcePeriodicRule" %>
<g:set var="rule" value="${item as ResourcePeriodicRule}"/>

<div class="item">
  <div class="right floated content">
    <ingame:link class="ui yellow icon basic button" resource="${rule}" action="edit" title="Редактировать">
      <i class="yellow edit icon"></i></ingame:link>
    <ingame:link class="ui red icon basic button" resource="${rule}" action="delete" title="Удалить"
                 onclick="return confirm('Вы уверены?');">
      <i class="red delete icon"></i></ingame:link>
  </div>
  <i class="green big wait icon"></i>
  <div class="content">
    <div class="header">Величина: ${rule.value} ${rule.target.type.measure ?: ''}</div>
    <div class="description">
      <g:if test="${!rule.fireDays}">
        Отключено
      </g:if>
      <g:else>
        ${rule.fireDays.collect {it.title}.join(', ')} в ${rule.formattedTime}
      </g:else>
    </div>
  </div>


</div>