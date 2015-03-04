<section>
    <h1>Доступные новостные ленты</h1>
    <ul class="property-list">
        <g:each in="${feeds}" var="feed">
            <li>
                <ingame:link resource="${feed}" action="show">${feed.title}</ingame:link>
            </li>
        </g:each>
    </ul>
</section>