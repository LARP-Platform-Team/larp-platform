<%@ page import="ru.srms.larp.platform.game.Game" %>

<div class="two fields">
    <div class="${hasErrors(bean: gameInstance, field: 'title', 'error')}  required  field">
        <label for="title">Название:</label>
        <g:textField name="title" required="" placeholder="Название"
                     value="${gameInstance?.title}"/>
    </div>

    <div class="${hasErrors(bean: gameInstance, field: 'alias', 'error')} required field">
        <label for="alias">Алиас:</label>
        <g:textField name="alias" placeholder="Алиас" required="" value="${gameInstance?.alias}"
                     pattern="${gameInstance.constraints.alias.matches}"/>
    </div>
</div>

<div class="${hasErrors(bean: gameInstance, field: 'overview', 'error')} required field">
    <label for="overview">Описание:</label>
    <g:textArea name="overview" cols="40" rows="5" maxlength="9999" required="" placeholder="Описание"
                value="${gameInstance?.overview}"/>
</div>

<div class="${hasErrors(bean: gameInstance, field: 'modules', 'error')} field">
  <label for="overview">Модули:</label>
      <g:select name="modules" from="${Game.GameModule.values()}"
              value="${gameInstance?.modules}" optionValue="title" multiple="true" class="ui dropdown"/>
</div>