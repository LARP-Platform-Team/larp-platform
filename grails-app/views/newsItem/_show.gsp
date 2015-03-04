
<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>

<article style="margin-bottom: 15px">
    <h2>${item.title}</h2>
    <g:render template="/shared/date" model="[date: item.created]"/>
    <p>${item.text}</p>
    <div class="buttons">
        <ingame:link controller="NewsItem" action="edit" id="${item.id}" class="edit">Править</ingame:link>
        <ingame:link controller="NewsItem" action="delete" id="${item.id}" class="delete"
                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
            Удалить</ingame:link>
    </div>
</article>
