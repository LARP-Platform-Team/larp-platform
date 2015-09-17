<%@ page import="ru.srms.larp.platform.game.Game" %>

<div class="two fields">
  <div class="${hasErrors(bean: gameInstance, field: 'title', 'error')}  required  field">
    <label for="title">Название:</label>
    <g:textField name="title" required="" placeholder="Название" value="${gameInstance?.title}"/>
    <div class="ui pointing label">Максимум 32 символа. Должно быть уникальным.</div>
  </div>

  <div class="${hasErrors(bean: gameInstance, field: 'alias', 'error')} required field">
    <label for="alias">Алиас:</label>
    <g:textField name="alias" placeholder="Алиас" required="" value="${gameInstance?.alias}"
                 pattern="${gameInstance.constraints.alias.matches}"/>
    <div class="ui pointing label">Так название вашей игры будет отображаться в ссылке.
    Максимум 32 символа, можно использовать латинские буквы, цифры и тире. Должно быть уникальным.</div>
  </div>
</div>

<div class="${hasErrors(bean: gameInstance, field: 'preview', 'error')} required field">
  <label for="preview">Краткое описание (512 символов):</label>
  <g:textArea name="preview" cols="40" rows="5" class="simple rich" maxlength="1000" required=""
              placeholder="Краткое описание" value="${gameInstance?.preview}"/>
  <div class="ui pointing label">Отображается на главной странице, в списке игр.
  Максимум 512 символов.</div>
</div>

<div class="${hasErrors(bean: gameInstance, field: 'overview', 'error')} required field">
  <label for="overview">Полное описание:</label>
  <g:textArea name="overview" cols="40" rows="5" class="rich" maxlength="9999" required=""
              placeholder="Описание" value="${gameInstance?.overview}"/>
</div>

<div class="${hasErrors(bean: gameInstance, field: 'modules', 'error')} field">
  <label for="overview">Модули:</label>
  <g:select name="modules" from="${Game.GameModule.values()}" class="ui dropdown"
            value="${gameInstance?.modules}" optionValue="title" multiple="true"/>
  <div class="ui pointing label">Выберите модули, которые вам понадобятся.
  Позже тут будет отдельный блок для их настройки.</div>
</div>