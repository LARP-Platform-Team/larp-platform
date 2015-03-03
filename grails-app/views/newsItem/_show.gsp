
<%@ page import="ru.srms.larp.platform.game.news.NewsItem" %>

<article style="margin-bottom: 15px">
    <h2>${item.title}</h2>
    <g:render template="/shared/date" model="[date: item.created]"/>
    <p>${item.text}</p>
</article>
