package ru.srms.larp.platform

/**
 * Created by Treble Snake on 25.09.2015.
 */
class UrlHelper {
  /**
   * Choose correct mapping for the current environment
   * @param attrs tag attrs map
   * @return modified attrs map
   */
  public static Map determineMapping(Map attrs, Map params) {
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
  public static Map composeAttrs(Map attrs, Map params, boolean checkAttrs = false) {
    if (!attrs.params)
      attrs.params = [:]

    // take game alias from defined tag attr, if needed
    if (checkAttrs && attrs.gameAlias)
      attrs.params.gameAlias = attrs.gameAlias
    // else from params scope, if it defined there and not explicitly defined in params tag attr
    else if (!attrs.params.gameAlias && params.gameAlias)
      attrs.params.gameAlias = params.gameAlias

    // same for char alias
    if (checkAttrs && attrs.charAlias)
      attrs.params.charAlias = attrs.charAlias
    else if (!attrs.params.charAlias && params.charAlias)
      attrs.params.charAlias = params.charAlias

    return attrs
  }
}
