package larp.platform

import org.codehaus.groovy.grails.web.mapping.UrlMapping
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.pages.TagLibraryLookup
import org.codehaus.groovy.grails.web.util.TypeConvertingMap
import org.springframework.web.servlet.support.RequestContextUtils

class GameTagLib {
    static namespace = "ingame"
//    static defaultEncodeAs = [taglib: 'html']
//    static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

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
    def link = { attrs, body ->
        out << g.link(determineMapping(composeAttrs(attrs, true)), body)
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
    def paginate = { Map attrs ->
        out << g.paginate(determineMapping(composeAttrs(attrs)))
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
    def form = { attrs, body ->
        attrs.url = composeAttrs(attrs.url)
        out << g.form(attrs, body)
    }

    /**
     * A form which uses the javascript provider to serialize its parameters and submit via an asynchronous ajax call.
     *
     * @attr name REQUIRED The form name
     * @attr url REQUIRED The url to submit to as either a map (containing values for the controller, action, id, and params) or a URL string
     * @attr action The action to execute as a fallback, defaults to the url if non specified
     * @attr update Either a map containing the elements to update for 'success' or 'failure' states, or a string with the element to update in which cause failure events would be ignored
     * @attr before The javascript function to call before the remote function call
     * @attr after The javascript function to call after the remote function call
     * @attr asynchronous Whether to do the call asynchronously or not (defaults to true)
     * @attr method The method to use the execute the call (defaults to "post")
     */
    def formRemote = { attrs, body ->
        attrs.url = composeAttrs(attrs.url)
        out << g.form(modifyRemoteAttrs(attrs), body)
    }

    /**
     * Attention! Use "url" attr to set action, controller, id, params, etc
     * Creates a link to a remote uri that can be invoked via ajax.
     *
     * @attr update a map containing the elements to update for 'success' or 'failure' states
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
     * @attr elementId the DOM element id
     * @attr gameAlias by default is extracted from params
     * @attr charAlias by default is extracted from params
     */
    def remoteLink = {attrs, body ->
        def newBody = new GroovyPage.ConstantClosure(body() +
                "<div class=\"ui mini inline loader\"></div>")
        if (attrs.url)
            attrs.url = composeAttrs(determineMapping(attrs.url))
        else
            attrs = composeAttrs(determineMapping(attrs), true)

        out << g.link(modifyRemoteAttrs(attrs), newBody)
    }

    /**
     * TODO move to UI Tag Lib
     * Creates next/previous links to support pagination for the current controller.<br/>
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
    Closure semanticPaginate = { Map attrsMap ->
        TypeConvertingMap attrs = (TypeConvertingMap)attrsMap
        def writer = out
        if (attrs.total == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }

        def messageSource = grailsAttributes.messageSource
        def locale = RequestContextUtils.getLocale(request)

        def total = attrs.int('total') ?: 0
        def offset = params.int('offset') ?: 0
        def max = params.int('max')
        def maxsteps = (attrs.int('maxsteps') ?: 10)

        if (!offset) offset = (attrs.int('offset') ?: 0)
        if (!max) max = (attrs.int('max') ?: 10)

        Map linkParams = [:]
        def gameParams = composeAttrs(determineMapping([:]))
        linkParams += gameParams.params


        if (attrs.params instanceof Map) linkParams.putAll((Map)attrs.params)
        linkParams.offset = offset - max
        linkParams.max = max
        if (params.sort) linkParams.sort = params.sort
        if (params.order) linkParams.order = params.order

        Map linkTagAttrs = [:]
        def action
        if (attrs.containsKey('mapping')) {
            linkTagAttrs.mapping = attrs.mapping
            action = attrs.action
        } else {
            action = attrs.action ?: params.action
        }
        if(gameParams.mapping)
            linkTagAttrs.mapping = gameParams.mapping

        if (action) {
            linkTagAttrs.action = action
        }
        if (attrs.controller) {
            linkTagAttrs.controller = attrs.controller
        }
        if (attrs.containsKey(UrlMapping.PLUGIN)) {
            linkTagAttrs.put(UrlMapping.PLUGIN, attrs.get(UrlMapping.PLUGIN))
        }
        if (attrs.containsKey(UrlMapping.NAMESPACE)) {
            linkTagAttrs.put(UrlMapping.NAMESPACE, attrs.get(UrlMapping.NAMESPACE))
        }
        if (attrs.id != null) {
            linkTagAttrs.id = attrs.id
        }
        if (attrs.fragment != null) {
            linkTagAttrs.fragment = attrs.fragment
        }
        linkTagAttrs.params = linkParams

        // determine paging variables
        def steps = maxsteps > 0
        int currentstep = ((offset / max) as int) + 1
        int firststep = 1
        int laststep = Math.round(Math.ceil(total / max)) as int

        linkTagAttrs.put('class', 'item')

        // display previous link when not on firststep unless omitPrev is true
        if (currentstep > firststep && !attrs.boolean('omitPrev')) {
//      linkTagAttrs.put('class', 'prevLink')
            linkParams.offset = offset - max
            writer << callLink((Map)linkTagAttrs.clone()) {
                (attrs.prev ?: messageSource.getMessage('paginate.prev', null, messageSource.getMessage('default.paginate.prev', null, 'Previous', locale), locale))
            }
        }

        // display steps when steps are enabled and laststep is not firststep
        if (steps && laststep > firststep) {
//      linkTagAttrs.put('class', 'step')

            // determine begin and endstep paging variables
            int beginstep = currentstep - (Math.round(maxsteps / 2.0d) as int) + (maxsteps % 2)
            int endstep = currentstep + (Math.round(maxsteps / 2.0d) as int) - 1

            if (beginstep < firststep) {
                beginstep = firststep
                endstep = maxsteps
            }
            if (endstep > laststep) {
                beginstep = laststep - maxsteps + 1
                if (beginstep < firststep) {
                    beginstep = firststep
                }
                endstep = laststep
            }

            // display firststep link when beginstep is not firststep
            if (beginstep > firststep && !attrs.boolean('omitFirst')) {
                linkParams.offset = 0
                writer << callLink((Map)linkTagAttrs.clone()) {firststep.toString()}
            }
            //show a gap if beginstep isn't immediately after firststep, and if were not omitting first or rev
            if (beginstep > firststep+1 && (!attrs.boolean('omitFirst') || !attrs.boolean('omitPrev')) ) {
                writer << '<div class="disabled item">..</div>'
            }

            // display paginate steps
            (beginstep..endstep).each { int i ->
                if (currentstep == i) {
                    writer << "<div class=\"active item\">${i}</div>"
                }
                else {
                    linkParams.offset = (i - 1) * max
                    writer << callLink((Map)linkTagAttrs.clone()) {i.toString()}
                }
            }

            //show a gap if beginstep isn't immediately before firststep, and if were not omitting first or rev
            if (endstep+1 < laststep && (!attrs.boolean('omitLast') || !attrs.boolean('omitNext'))) {
                writer << '<div class="disabled item">..</div>'
            }
            // display laststep link when endstep is not laststep
            if (endstep < laststep && !attrs.boolean('omitLast')) {
                linkParams.offset = (laststep - 1) * max
                writer << callLink((Map)linkTagAttrs.clone()) { laststep.toString() }
            }
        }

        // display next link when not on laststep unless omitNext is true
        if (currentstep < laststep && !attrs.boolean('omitNext')) {
//      linkTagAttrs.put('class', 'nextLink')
            linkParams.offset = offset + max
            writer << callLink((Map)linkTagAttrs.clone()) {
                (attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null, messageSource.getMessage('default.paginate.next', null, 'Next', locale), locale))
            }
        }
    }

    TagLibraryLookup gspTagLibraryLookup
    private callLink(Map attrs, Object body) {
        GroovyPage.captureTagOutput(gspTagLibraryLookup, 'g', 'link', attrs, body, webRequest)
    }

    /**
     * Choose correct mapping for the current environment
     * @param attrs tag attrs map
     * @return modified attrs map
     */
    private Map determineMapping(Map attrs) {
        if (!attrs.mapping) {
            if (params.charAlias)
                attrs.mapping = 'inGame'
            else if (params.gameAlias)
                attrs.mapping = 'gameAdmin'
        }
        return attrs
    }

    /**
     * Add in-game info to the tag attrs
     * @param attrs tag attrs map
     * @param checkAttrs if {@code true} - look for in-game info in tag attrs
     * @return modified attrs map
     */
    private Map composeAttrs(Map attrs, boolean checkAttrs = false) {
        if (!attrs.params)
            attrs.params = [:]

        // take game alias from defined tag attr, if needed
        if(checkAttrs && attrs.gameAlias)
            attrs.params.gameAlias = attrs.gameAlias
        // else from params scope, if it defined there and not explicitly defined in params tag attr
        else if (!attrs.params.gameAlias && params.gameAlias)
            attrs.params.gameAlias = params.gameAlias

        // same for char alias
        if(checkAttrs && attrs.charAlias)
            attrs.params.charAlias = attrs.charAlias
        else if (!attrs.params.charAlias && params.charAlias)
            attrs.params.charAlias = params.charAlias

        return attrs
    }

    private Map modifyRemoteAttrs(Map attrs) {
        if (!attrs.params)
            attrs.params = [:]

        attrs.class = (attrs.class ?: "") + " larp-ajax"

        if (attrs.update) {
            if (attrs.update.success)
                attrs['data-output-success'] = attrs.update.success
            if (attrs.update.failure)
                attrs['data-output-failure'] = attrs.update.failure

            attrs.remove('update')
        }

        return attrs
    }
}
