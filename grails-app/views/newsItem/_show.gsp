
<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>

<article style="margin-bottom: 15px">
    <h2>${item.title}</h2>
    <g:render template="/shared/date" model="[date: item.created]"/>
    <p>${item.text}</p>
    <div class="buttons">
        <sec:permitted object="${item.feed}" permission="write">
        <ingame:link controller="NewsItem" action="edit" id="${item.id}" class="edit">Править</ingame:link>
        </sec:permitted>
        <sec:permitted object="${item.feed}" permission="delete">
        <ingame:link controller="NewsItem" action="delete" id="${item.id}" class="delete"
                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
            Удалить</ingame:link>
        </sec:permitted>
    </div>
</article>
