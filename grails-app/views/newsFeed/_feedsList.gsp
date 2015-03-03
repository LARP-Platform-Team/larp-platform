<section>
    <h1>Доступные новостные ленты</h1>
    <ul class="property-list">
        <g:each in="${feeds}" var="feed">
            <li>
                <g:gameLink resource="${feed}" action="show">${feed.title}</g:gameLink>
            </li>
        </g:each>
    </ul>
</section>