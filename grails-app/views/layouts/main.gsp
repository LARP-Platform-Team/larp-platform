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
  <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" sizes="114x114"
        href="${assetPath(src: 'apple-touch-icon-retina.png')}">

  <asset:stylesheet src="application.css"/>
  <asset:javascript src="application.js"/>

  <title><g:layoutTitle default="Grails"/></title>

  <g:layoutHead/>
</head>

<body>

<div id="mainWrapper" class="ui container">

  <header id="mainHeader" class="ui attached two column stackable grid segment">

    <div class="left floated column">
      <a target="_blank" class="ui violet button"
         href="https://bitbucket.org/TrebleSnake/larp-platform/wiki/Home">Платформа городских ролевочек</a>
    </div>

    <div class="right four wide floated column">
      <sec:ifLoggedIn>
        <div class="ui card">
          <div class="content">
            <img class="right floated mini ui image" src="/images/someavatar.gif">
            <div class="header"><sec:loggedInUserInfo field="username"/></div>
        <div class="meta">Some info...</div>
        </div>
        <div class="extra content">
          <div class="ui two tiny buttons">
            <a class="ui basic violet button"
               href="${createLink(controller: 'account', id: sec.loggedInUserInfo([field: 'id']))}">Кабинет</a>
            <a class="ui basic violet button"
               href="${createLink(uri: '/j_spring_security_logout')}">Выйти</a>
          </div>
        </div>
      </sec:ifLoggedIn>

      <sec:ifNotLoggedIn>
        <g:link controller="login" action="auth">Войти</g:link>
      </sec:ifNotLoggedIn>
    </div>
  </header>

  <nav class="ui attached segment">
    <g:render template="/shared/semantic/breadcrumbs"/>
  </nav>

  <main class="ui attached segment">
    <g:if test="${flash.error}">
      <div class="errors" role="status">${flash.error}</div>
    </g:if>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:layoutBody/>

  </main>

  <footer class="ui bottom attached segment">
  &copy; S.R.M.S.
  </footer>
</div>
</html>
