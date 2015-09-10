<%@ page import="org.springframework.validation.FieldError; ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${mailBoxInstance as MailBox}"/>
  <g:set var="title" value="${subject.address}: адресная книга"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" action="show" resource="${subject}">
    <i class="arrow left grey icon"></i> Назад</ingame:link>
</content>

<content tag="content">
  <div class="ui grid">
    <div class="eight wide column">
      <g:if test="${subject.savedAddresses}">
        <div class="ui relaxed divided selection list">
          <g:each in="${subject.savedAddresses}" var="address">
            <div class="item">
              <div class="right floated content">
                <ingame:link class="ui icon red basic button" title="Удалить"
                             action="deleteSavedAddress" resource="${subject}"
                             onclick="return confirm('Вы уверены?');"
                             params="['entry.id': address.id]">
                  <i class="red delete icon"></i>
                </ingame:link>
              </div>

              <i class="mail icon"></i>

              <div class="content">
                <div
                    class="header">${address.entry ? address.entry.address : address.entryAddress}</div>
                <g:if test="${address.entry}">
                  <div class="description">${address.entry.name}</div>
                </g:if>
                <g:else>
                  <div class="ui red tiny basic label"><i
                      class="ban red icon"></i> Адрес не существует</div>
                </g:else>
              </div>
            </div>
          </g:each>
        </div>
      </g:if>
      <g:else>
        <ui:message type="info">В адресной книге еще нет ни одной записи.</ui:message>
      </g:else>
    </div>

    <div class="ui eight wide column">
      <section class="ui pilled segment">
        <div class="ui blue ribbon label">Добавить адрес</div>
        <div class="ui hidden divider"></div>

        <g:render template="/shared/fromErrors" bean="${addAddressCommand}" var="subject"/>
        <ingame:form class="ui form" url="[resource: subject, action: 'addSavedAddress']">
          <div class="${hasErrors(bean: addAddressCommand, field: 'newAddress', 'error')} required field">
            <label for="newAddress">Адрес</label>
            <g:textField name="newAddress" required="" value="${addAddressCommand?.newAddress}"/>
          </div>
          <ui:submit icon="add">Добавить</ui:submit>
        </ingame:form>

      </section>
    </div>
  </div>

</content>

</body>
</html>
