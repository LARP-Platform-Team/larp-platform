package larp.platform

class GameTagLib {
//    static defaultEncodeAs = [taglib: 'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    /**
     * Extends general linking to controllers, actions etc.<br/>
     * GameAlias and CharAlias are extracted from params by default<br/>
     *
     * @attr controller The name of the controller to use in the link, if not specified the current controller will be linked
     * @attr action The name of the action to use in the link, if not specified the default action will be linked
     * @attr uri relative URI
     * @attr url A map containing the action,controller,id etc.
     * @attr base Sets the prefix to be added to the link target address, typically an absolute server URL. This overrides the behaviour of the absolute property, if both are specified.
     * @attr absolute If set to "true" will prefix the link target address with the value of the grails.serverURL property from Config, or http://localhost:&lt;port&gt; if no value in Config and not running in production.
     * @attr id The id to use in the link
     * @attr fragment The link fragment (often called anchor tag) to use
     * @attr params A map containing URL query parameters
     * @attr mapping The named URL mapping to use to rewrite the link
     * @attr event Webflow _eventId parameter
     * @attr elementId DOM element id
     * @attr gameAlias by default is extracted from params
     * @attr charAlias by default is extracted from params
     */
    def gameLink = { attrs, body ->
        if (!attrs.params)
            attrs.params = [:]

        if (!attrs.params.gameAlias)
            attrs.params.gameAlias = attrs.gameAlias ?: params.gameAlias

        def charAlias = attrs.charAlias ?: params.charAlias
        if (!attrs.params.charAlias && charAlias)
            attrs.params.charAlias = charAlias

        out << g.link(defineMapping(attrs), body)
    }

    /**
     * Extends next/previous links to support pagination for the current controller.<br/>
     * GameAlias and CharAlias are extracted from params by default<br/>
     * Mapping defined by presence of GameAlias and CharAlias in params<br/>
     *
     * &lt;g:paginate total="${Account.count()}" /&gt;<br/>
     *
     * @emptyTag
     *
     * @attr total REQUIRED The total number of results to paginate
     * @attr action the name of the action to use in the link, if not specified the default action will be linked
     * @attr controller the name of the controller to use in the link, if not specified the current controller will be linked
     * @attr id The id to use in the link
     * @attr params A map containing request parameters
     * @attr prev The text to display for the previous link (defaults to "Previous" as defined by default.paginate.prev property in I18n messages.properties)
     * @attr next The text to display for the next link (defaults to "Next" as defined by default.paginate.next property in I18n messages.properties)
     * @attr omitPrev Whether to not show the previous link (if set to true, the previous link will not be shown)
     * @attr omitNext Whether to not show the next link (if set to true, the next link will not be shown)
     * @attr omitFirst Whether to not show the first link (if set to true, the first link will not be shown)
     * @attr omitLast Whether to not show the last link (if set to true, the last link will not be shown)
     * @attr max The number of records displayed per page (defaults to 10). Used ONLY if params.max is empty
     * @attr maxsteps The number of steps displayed for pagination (defaults to 10). Used ONLY if params.maxsteps is empty
     * @attr offset Used only if params.offset is empty
     * @attr mapping The named URL mapping to use to rewrite the link
     * @attr fragment The link fragment (often called anchor tag) to use
     */
    def gamePaginate = { Map attrs ->
        out << g.paginate(defineMapping(composeAttrs(attrs)))
    }

    /**
     * Extends General linking to controllers, actions etc. Examples:<br/>
     * GameAlias and CharAlias are extracted from params by default<br/>
     *
     * &lt;g:form action="myaction"&gt;...&lt;/g:form&gt;<br/>
     * &lt;g:form controller="myctrl" action="myaction"&gt;...&lt;/g:form&gt;<br/>
     *
     * @attr action the name of the action to use in the link, if not specified the default action will be linked
     * @attr controller the name of the controller to use in the link, if not specified the current controller will be linked
     * @attr id The id to use in the link
     * @attr url A map containing the action,controller,id etc.
     * @attr name A value to use for both the name and id attribute of the form tag
     * @attr useToken Set whether to send a token in the request to handle duplicate form submissions. See Handling Duplicate Form Submissions
     * @attr method the form method to use, either 'POST' or 'GET'; defaults to 'POST'
     */
    def gameForm = { attrs, body ->
        attrs.url = composeAttrs(attrs.url)
        out << g.form(attrs, body)
    }

    // TODO think about links
    private Map defineMapping(Map attrs) {
        if (!attrs.mapping) {
            if (params.charAlias)
                attrs.mapping = 'inGame'
            else if (params.gameAlias)
                attrs.mapping = 'gameAdmin'
        }
        return attrs
    }

    private Map composeAttrs(Map attrs) {
        if (!attrs.params)
            attrs.params = [:]

        if (!attrs.params.gameAlias && params.gameAlias)
            attrs.params.gameAlias = params.gameAlias

        if (!attrs.params.charAlias && params.charAlias)
            attrs.params.charAlias = params.charAlias

        return attrs
    }
}
