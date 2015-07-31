<g:set var="outcome" value="${log.source == parent}"/>
<g:set var="target" value="${outcome ? log.target : log.source}"/>


<span class="date">[<g:render template="/shared/date" model="[date: log.date]"/>]</span>
<span class="type">${outcome ? "Исходящий" : "Входящий"} перевод</span>
<span class="target">${outcome ? "Получатель" : "Отправитель"}:
  <span class="targetId">${target.fullId}</span>
</span>
<span class="value">Значение: ${log.value}</span>