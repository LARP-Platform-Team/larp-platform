<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance; ru.srms.larp.platform.game.character.GameCharacter" %>
<g:set var="subject" value="${subject as ResourceInstance}"/>

<div class="ui three fields">
  <div class="${hasErrors(bean: subject, field: 'title', 'error')} required field">
    <label for="title">Название</label>
    <g:field type="text" name="title" required="" value="${subject?.title}"/>
    <div class="ui pointing label">Максимум 64 символа. Должно быть уникальным в рамках типа ресурса.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'identifier', 'error')} required field">
    <label for="identifier">Идентификатор</label>
    <g:field type="text" name="identifier" required="" value="${subject?.identifier}"/>
    <div class="ui pointing label">Максимум 64 символа. Должен быть уникальным в рамках типа ресурса.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'value', 'error')} required field">
    <label for="value">Количество ресурса</label>
    <g:field type="text" name="value" required="" value="${subject?.value}"/>
  </div>
</div>

<div class="ui three fields">
  <div class="${hasErrors(bean: subject, field: 'minValue', 'error')} field">
    <label for="minValue">Минимальное значение</label>
    <g:field type="text" name="minValue" value="${subject?.minValue != null ? subject.minValue : subject?.type?.minValue}"/>
    <div class="ui pointing label">Оставьте пустым, если минимальное значение не нужно.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'maxValue', 'error')} field">
    <label for="maxValue">Максимальное значение</label>
    <g:textField name="maxValue" value="${subject?.maxValue != null ? subject.maxValue : subject?.type?.maxValue}"/>
    <div class="ui pointing label">Оставьте пустым, если максимальное значение не нужно.</div>
  </div>
</div>

<div class="ui three fields">
<div class="${hasErrors(bean: subject, field: 'origin', 'error')} field">
  <label for="origin.id">Источник</label>
  <g:select name="origin.id" from="${subject.type.origins}" value="${subject.origin?.id}"
            class="dropdown"
            optionKey="id" optionValue="title" noSelection="${['null': 'нет']}"/>
</div>

<div class="${hasErrors(bean: subject, field: 'owner', 'error')} field">
  <label for="owner.id">Владелец</label>
  <g:select name="owner.id" from="${GameCharacter.findAllByGame(params.game)}" class="dropdown"
            value="${subject.owner?.id}" optionKey="id" noSelection="${['null': 'нет']}"/>
  <div class="ui pointing label">Игрок, управляющий ресурсом. Можно оставить пустым - это будет мастерский ресурс.</div>
</div>
</div>


<div class="${hasErrors(bean: subject, field: 'transferable', 'error')} inline field">
  <div class="ui toggle checkbox">
    <g:checkBox name="transferable" value="${subject?.transferable}"/>
    <label for="transferable">Можно переводить средства?</label>
  </div>
</div>

<div class="${hasErrors(bean: subject, field: 'ownerEditable', 'error')} inline field">
  <div class="ui toggle checkbox">
    <g:checkBox name="ownerEditable" value="${subject?.ownerEditable}"/>
    <label for="ownerEditable">Игрок может менять значение напрямую?</label>
  </div>
</div>

<g:hiddenField name="type.id" id="type" value="${subject.type.id}"/>