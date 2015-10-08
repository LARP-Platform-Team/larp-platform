<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<section class="ui segment">
  <ingame:form method="GET" class="ui form" url="[action: 'index']">
    <div class="fields">
      <div class="four wide field">
        <label for="filter.code">Код</label>
        <g:textField name="filter.code" value="${params.filter?.code}"/>
      </div>

      <div class="four wide field">
        <label for="filter.title">Название</label>
        <g:textField name="filter.title" value="${params.filter?.title}"/>
      </div>

      <div class="six wide field">
        <label for="filter.owner">Владелец:</label>
        <g:select name="filter.owner" multiple="true"
                  from="${GameCharacter.findAllByGame(params.game)}"
                  optionKey="id" class="ui dropdown"
                  value="${GameCharacter.findAllByIdInList(params.filter?.list('owner'))}"/>
      </div>

      <div class="field">
        <label>&nbsp;</label>
        <ui:submit icon="search" title="Найти"/>
        <ingame:link action="index" title="Очистить" class="ui basic icon button">
          <i class="close icon"></i>
        </ingame:link>
      </div>
    </div>
  </ingame:form>
</section>