<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${g.message(code: 'spring.security.ui.register.title')}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">

  <g:if test='${emailSent}'>
    <ui:message type="info"><g:message code='spring.security.ui.register.sent'/></ui:message>
  </g:if>
  <g:else>
    <g:if test="${command}">
      <g:render template="/shared/fromErrors" bean="${command}" var="subject"/>
    </g:if>
    <g:form action='register' name='registerForm' class="ui form">

      <div class="${hasErrors(bean: command, field: 'username', 'error')} inline required field">
        <label for="username">Логин</label>
        <g:textField name="username" required="required" value="${command?.username}"/>
      </div>

      <div class="${hasErrors(bean: command, field: 'email', 'error')} inline required field">
        <label for="email">E-mail</label>
        <g:textField name="email" type="email" required="required" value="${command?.email}"/>
      </div>

      <div class="${hasErrors(bean: command, field: 'password', 'error')} inline required field">
        <label for="password">Пароль</label>
        <g:passwordField name="password" required="required"  value="${command?.password}"/>
      </div>

      <div class="${hasErrors(bean: command, field: 'password2', 'error')} inline required field">
        <label for="password2">Повторите пароль</label>
        <g:passwordField name="password2" required="required" value="${command?.password2}"/>
      </div>

      <ui:submit name="doRegister" icon="privacy">
        <g:message code='spring.security.ui.register.submit'/></ui:submit>

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
