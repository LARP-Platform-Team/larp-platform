<%@ page import="ru.srms.larp.platform.sec.ui.ResetPasswordCommand" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${g.message(code: 'spring.security.ui.forgotPassword.title')}"/>
  <g:set var="command" value="${resetPasswordCommandInstance as ResetPasswordCommand}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">
  <g:render template="/shared/fromErrors" bean="${command}" var="subject"/>
  <g:form action='setNewPassword' class="ui form">
    <div class="required field">
      <label for="password">Новый пароль</label>
      <g:passwordField name="password" required="required"/>
    </div>

    <div class="required field">
      <label for="password2">Повторите новый пароль</label>
      <g:passwordField name="password2" required="required"/>
    </div>

    <g:hiddenField name="token" value="${params.token ?: command.token}"/>

    <ui:submit name="doSetPassword" icon="privacy">Сохранить</ui:submit>
  </g:form>
</content>
</body>
</html>
