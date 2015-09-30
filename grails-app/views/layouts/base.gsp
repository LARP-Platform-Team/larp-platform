<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
  <link rel="icon" type="image/png" href="${assetPath(src: 'favicon-32x32.png')}" sizes="32x32">
  <link rel="icon" type="image/png" href="${assetPath(src: 'favicon-96x96.png')}" sizes="96x96">
  <link rel="icon" type="image/png" href="${assetPath(src: 'favicon-16x16.png')}" sizes="16x16">

  <script>
    var CKEDITOR_BASEPATH = '/assets/';
  </script>
  <asset:stylesheet src="application.css"/>
  <asset:javascript src="application.js"/>
  <title><g:layoutTitle default="Платформа городских ролевок"/></title>

  <g:layoutHead/>
</head>

<body>

<div id="mainWrapper" class="ui container">

  <header id="mainHeader" class="ui attached two column stackable grid segment">

    <div class="left floated column">
      <a target="_blank" class="ui blue compact button"
         href="https://vk.com/larp_platform">Группа ВКонтакте</a><br/><br/>
      <a target="_blank" class="ui violet compact button"
         href="https://github.com/LARP-Platform-Team/larp-platform/">Репозиторий GitHub</a>
    </div>

    <div class="right four wide floated column">
      <sec:ifLoggedIn>
        <div class="ui card">
          <div class="content">
            <img class="right floated mini ui image" src="${assetPath(src: 'dummy.png')}">
            <div class="header"><sec:loggedInUserInfo field="name"/></div>
        <div class="meta">Some info...</div>
        </div>
        <div class="extra content">
          <div class="ui two tiny buttons">
            <a class="ui basic violet button"
               href="${createLink(controller: 'account', id: sec.loggedInUserInfo([field: 'id']))}">Кабинет</a>
            <a class="ui basic pink button"
               href="${createLink(uri: '/j_spring_security_logout')}">Выйти</a>
          </div>
        </div>
      </sec:ifLoggedIn>

      <sec:ifNotLoggedIn>
        <g:link controller="login" action="auth" class="ui basic violet button">Войти</g:link>
      </sec:ifNotLoggedIn>
    </div>
  </header>

  <nav class="ui attached segment">
    <g:render template="/shared/semantic/breadcrumbs"/>
  </nav>

  <main class="ui attached segment">
    <h1><g:layoutTitle/></h1>
    <div class="ui divider"></div>
    <g:render template="/layouts/basicFlash"/>
    <g:layoutBody/>
    <div class="ui hidden clearing divider"></div>
  </main>

  <footer class="ui bottom attached segment">
  &copy; S.R.M.S.
  </footer>
</div>

</body>
</html>
