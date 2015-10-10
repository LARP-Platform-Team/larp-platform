<%@ page import="ru.srms.larp.platform.sec.ui.RegisterCommand" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${g.message(code: 'spring.security.ui.register.title')}"/>
  <g:set var="command" value="${registerCommandInstance as RegisterCommand}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">

  <g:if test='${emailSent}'>
    <ui:message type="info"><g:message code='spring.security.ui.register.sent'/></ui:message>
  </g:if>
  <g:else>
    <g:render template="/shared/fromErrors" bean="${command}" var="subject"/>
    <g:form action='register' name='registerForm' class="ui form">
      <div class="${hasErrors(bean: command, field: 'username', 'error')} required field">
        <div class="ui grid">
          <div class="middle aligned right aligned four wide column">
            <div class="required field">
              <label for="username">Логин (не отображается)</label>
            </div>
          </div>

          <div class="middle aligned five wide left aligned column">
            <g:textField name="username" required="required" value="${command?.username}"/>
          </div>

          <div class="middle aligned seven wide left aligned column">
            <div
                class="ui left pointing label">Максимум 64 символа. Можно использовать латинские буквы, цифры, тире,
            точку и нижнее подчеркивание.</div>
          </div>
        </div>
      </div>

      <div class="${hasErrors(bean: command, field: 'name', 'error')} required field">
        <div class="ui grid">
          <div class="middle aligned right aligned four wide column">
            <div class="required field">
              <label for="username">Имя</label>
            </div>
          </div>

          <div class="middle aligned five wide left aligned column">
            <g:textField name="name" required="required" value="${command?.name}"/>
          </div>

          <div class="middle aligned seven wide left aligned column">
            <div
                class="ui left pointing label">Максимум 64 символа. Можно использовать латинские и русские буквы,
            пробел, цифры, тире, точку, запятую и нижнее подчеркивание.</div>
          </div>
        </div>
      </div>

      <div class="${hasErrors(bean: command, field: 'email', 'error')} required field">
        <div class="ui grid">
          <div class="middle aligned right aligned four wide column">
            <div class="required field">
              <label for="email">E-mail</label>
            </div>
          </div>

          <div class="middle aligned five wide left aligned column">
            <g:textField name="email" type="email" required="required" value="${command?.email}"/>
          </div>
        </div>
      </div>

      <div class="${hasErrors(bean: command, field: 'password', 'error')} required field">
        <div class="ui grid">
          <div class="middle aligned right aligned four wide column">
            <div class="required field">
              <label for="password">Пароль</label>
            </div>
          </div>

          <div class="middle aligned five wide left aligned column">
            <g:passwordField name="password" required="required" value="${command?.password}"/>
          </div>
        </div>
      </div>

      <div class="${hasErrors(bean: command, field: 'password2', 'error')} required field">
        <div class="ui grid">
          <div class="middle aligned right aligned four wide column">
            <div class="required field">
              <label for="password2">Повторите пароль</label>
            </div>
          </div>

          <div class="middle aligned five wide left aligned column">
            <g:passwordField name="password2" required="required" value="${command?.password2}"/>
          </div>
        </div>
      </div>

      <div class="ui grid">
        <div class="middle aligned right aligned four wide column"></div>

        <div class="middle aligned left aligned five wide column">
          <ui:submit name="doRegister" icon="privacy">
            <g:message code='spring.security.ui.register.submit'/>
          </ui:submit>
        </div>
      </div>

    </g:form>
  </g:else>

  <script>
    $(document).ready(function () {
      $('#username').focus();
    });
  </script>
</content>
</body>
</html>
