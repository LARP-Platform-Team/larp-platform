<%@ page import="ru.srms.larp.platform.game.resources.ResourcePeriodicRule; ru.srms.larp.platform.game.resources.PeriodicRule; ru.srms.larp.platform.game.resources.ResourceInstance" %>
<g:set var="subject" value="${subject as ResourcePeriodicRule}"/>
<div class="ui three fields">
  <div class="${hasErrors(bean: subject, field: 'value', 'error')} required field">
    <label for="value">Значение изменения</label>
    <g:field type="text" name="value" required="" value="${subject?.value}"/>
  </div>

  <div class="${hasErrors(bean: subject, field: 'comment', 'error')} field">
    <label for="comment">Комментарий для лога перевода</label>
    <g:field type="text" name="comment" value="${subject?.comment}"/>
  </div>
</div>

<div class="ui three fields">

  <div class="${hasErrors(bean: subject, field: 'source', 'error')} field">
    <label for="source.id">Источник перевода</label>
    <g:select name="source.id" from="${ResourceInstance.findAllByType(subject?.target?.type)}" class="dropdown"
              value="${subject?.source?.id}" optionValue="fullId" optionKey="id" noSelection="${['null': 'нет']}"/>

  </div>

  <div class="${hasErrors(bean: subject, field: 'sourceName', 'error')} field">
    <label for="sourceName">Название источника перевода</label>
    <g:field type="text" name="sourceName" value="${subject?.sourceName}"/>
  </div>

</div>

<div class="ui field">
  <label>Время изменения</label>
</div>
<div class="ui fields">
  <g:each in="${ResourcePeriodicRule.WeekDays.values()}" var="day">
    <div class="ui field">
      <label for="${day}">${day.title}</label>
      <g:checkBox class="ui checkbox" name="${day.toString()}" value="${subject.fireDays?.contains(day)}"/>
    </div>
  </g:each>

  <div class="${hasErrors(bean: subject, field: 'fireHour', 'error')} field">
    <label for="fireHour">Часов</label>
    <g:field type="text" name="fireHour" value="${String.format("%02d", subject?.fireHour)}"/>
  </div>
  <div class="${hasErrors(bean: subject, field: 'fireMinute', 'error')} field">
    <label for="fireMinute">Минут</label>
    <g:field type="text" name="fireMinute" value="${String.format("%02d", subject?.fireMinute)}"/>
  </div>
</div>

<g:hiddenField name="target.id" id="type" value="${subject.target.id}"/>