<div class="ui three fields">
  <div class="${hasErrors(bean: gameResourceInstance, field: 'title', 'error')} required field">
    <label for="title">Название ресурса</label>
    <g:field type="text" name="title" required="" value="${gameResourceInstance?.title}"/>
    <div class="ui pointing label">Например, Деньги. Максимум 64 символа. Должно быть уникальным в рамках игры.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'storage', 'error')} required field">
  <label for="storage">Хранилище</label>
  <g:field type="text" name="storage" required="" value="${subject?.storage}"/>
  <div class="ui pointing label">Например, счет. Максимум 64 символа.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'identifierTitle', 'error')} required field">
    <label for="identifierTitle">ID хранилища</label>
    <g:field type="text" name="identifierTitle" required=""
             value="${gameResourceInstance?.identifierTitle}"/>
    <div class="ui pointing label">Например, номер счета. Максимум 64 символа.</div>
  </div>

  <div class="${hasErrors(bean: gameResourceInstance, field: 'measure', 'error')} field">
    <label for="measure">Единица измерения</label>
    <g:textField name="measure" value="${gameResourceInstance?.measure}"/>
    <div class="ui pointing label">Максимум 16 символов.</div>
  </div>
</div>

<div class="ui three fields">
  <div class="${hasErrors(bean: gameResourceInstance, field: 'minValue', 'error')} field">
    <label for="minValue">Минимальное значение при создании</label>
    <g:field type="text" name="minValue" value="${gameResourceInstance?.minValue}"/>
    <div class="ui pointing label">Оставьте пустым, если минимальное значение не нужно.</div>
  </div>

  <div class="${hasErrors(bean: gameResourceInstance, field: 'maxValue', 'error')} field">
    <label for="maxValue">Максимальное значение при создании</label>
    <g:textField name="maxValue" value="${gameResourceInstance?.maxValue}"/>
    <div class="ui pointing label">Оставьте пустым, если максимальное значение не нужно.</div>
  </div>
</div>

<g:hiddenField name="game.id" id="game" value="${params.game.id}"/>