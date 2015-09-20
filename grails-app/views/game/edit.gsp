<%@ page import="ru.srms.larp.platform.sec.SpringUser; org.springframework.validation.FieldError; ru.srms.larp.platform.game.Game" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="subject" value="${gameInstance as Game}"/>
    <g:set var="title" value="Параметры игры ${subject.title}"/>
    <title>${title}</title>
</head>

<body>

<content tag="content">
    <div class="ui two column stackable grid">
        <div class="ten wide column">
            <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
            <g:form class="ui form" url="[resource: subject, action: 'update']" method="POST">
                <g:render template="form"/>
                <g:hiddenField name="version" value="${subject?.version}"/>
                <ui:submit icon="checkmark">Сохранить</ui:submit>
            </g:form>
        </div>

        <div class="six wide column">
            <section class="ui pilled segment">
                <div class="ui red ribbon label">Мастера</div>
                <ingame:formRemote name="masters" class="ui form" url="[resource: subject, action: 'addMaster']"
                                   update="[success: 'gameMasters', failure: 'gameMastersError']" method="POST">
                    <div id="gameMasters" class="ui middle aligned selection list larp-ajax-container">
                        <g:render template="masters" model="[masters: subject.masters]"/>
                    </div>
                    <div id="gameMastersError"></div>
                    <div class="ui divider"></div>

                    <div class="ui action input">
                        <g:select name="masterId" class="ui dropdown" from="${SpringUser.list()}"
                                  optionKey="id"/>
                        <ui:submit remote="true" class="right labeled" icon="add user">Добавить</ui:submit>
                    </div>
                </ingame:formRemote>
            </section>
        </div>
    </div>
</content>

</body>
</html>
