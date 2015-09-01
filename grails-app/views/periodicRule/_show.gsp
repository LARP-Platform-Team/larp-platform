<%@ page import="ru.srms.larp.platform.game.resources.PeriodicRule" %>
<g:set var="rule" value="${item as PeriodicRule}"/>

<div>
  ${rule.id}: ${rule.value}
  <ingame:link resource="${rule}" action="edit">edit</ingame:link>
  <ingame:link resource="${rule}" action="delete">delete</ingame:link>
</div>