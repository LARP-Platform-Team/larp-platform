<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${g.message(code: 'spring.security.ui.forgotPassword.title')}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">

  <ui:message type="info"><g:message code="spring.security.ui.forgotPassword.description"/></ui:message>
  <g:form action='restorePassword' name='passwordRestoreForm' class="ui form">
    <div class="required field">
      <label for="username">Логин или E-mail</label>
      <g:textField name="username" required="required"/>
    </div>
    <ui:submit name="sendEmail" title="${g.message(code: 'spring.security.ui.forgotPassword.submit')}">
      Выслать ссылку
      </ui:submit>
    </g:form>

  <script>
    $(document).ready(function () {
      $('#username').focus();
    });
  </script>
</content>
</body>
</html>
