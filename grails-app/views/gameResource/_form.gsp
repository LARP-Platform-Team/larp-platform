<div class="ui three fields">
  <div class="${hasErrors(bean: gameResourceInstance, field: 'title', 'error')} required field">
    <label for="title">Название</label>
    <g:field type="text" name="title" required="" value="${gameResourceInstance?.title}"/>
  </div>

  <div class="${hasErrors(bean: subject, field: 'identifierTitle', 'error')} field">
    <label for="identifierTitle">Название идентификатора</label>
    <g:field type="text" name="identifierTitle" required=""
             value="${gameResourceInstance?.identifierTitle}"/>
  </div>

  <div class="${hasErrors(bean: gameResourceInstance, field: 'measure', 'error')} field">
    <label for="measure">Единица измерения</label>
    <g:textField name="measure" value="${gameResourceInstance?.measure}"/>
  </div>
</div>

<div class="ui three fields">
  <div class="${hasErrors(bean: gameResourceInstance, field: 'minValue', 'error')} field">
    <label for="minValue">Минимальное значение по умолчанию</label>
    <g:field type="text" name="minValue" value="${gameResourceInstance?.minValue}"/>
    <div class="ui pointing label"><b>Не</b> применится для существующих экземпляров</div>
  </div>

  <div class="${hasErrors(bean: gameResourceInstance, field: 'maxValue', 'error')} field">
    <label for="maxValue">Максимальное значение по умолчанию</label>
    <g:textField name="maxValue" value="${gameResourceInstance?.maxValue}"/>
    <div class="ui pointing label"><b>Не</b> применится для существующих экземпляров</div>
  </div>
</div>

<g:hiddenField name="game.id" id="game" value="${params.game.id}"/>