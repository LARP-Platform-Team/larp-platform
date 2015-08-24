<%@ page import="ru.srms.larp.platform.sec.SpringUser; ru.srms.larp.platform.game.Game" %>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'name', 'error')} required field">
    <label for="name">Имя</label>
    <g:textField name="name" required="" placeholder="Иван"
                 value="${gameCharacterInstance?.name}"/>
</div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'alias', 'error')} required field">
    <label for="name">Алиас</label>
    <g:textField name="alias" required="" placeholder="ivan"
                 value="${gameCharacterInstance?.alias}"/>
</div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'player', 'error')} required field">
    <label for="name">Игрок</label>
    <g:select name="player" class="dropdown" from="${SpringUser.list()}"
              optionKey="id" optionValue="username" noSelection="${['null': 'нет']}"
              value="${gameCharacterInstance?.player?.id}"/>
</div>