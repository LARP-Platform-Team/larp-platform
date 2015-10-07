package ru.srms.larp.platform.breadcrubms

/**
 * Descriptor of the breadcrumb.
 * Contains information about current and parent breadcrumbs.
 *
 * @author Treble Snake
 */
class Descriptor {

  /** Key for the default descriptor - it's used if no other descriptor found */
  public static final String DEFAULT_KEY = 'default'

  /** Route to the current breadcrumb (controller, action, id, etc) */
  Map route

  /** Route to the parent breadcrumb (controller, action, id, etc) */
  Map parentRoute

  /** Action to produce title of the breadcrumb. Takes Map (params scope) as a parameter */
  Closure<String> composeTitle

  /**
   * <p>Modifies route according to the runtime data.</p>
   * <p>Takes two arguments:</p>
   * <ul>
   *   <li>Map route - map with current parent route value</li>
   *   <li>Map params - map with request params</li>
   * </ul>
   * <p>Returns modified route</p>
   * */
  Closure<Map> modifyParentRoute

  /**
   * @param parentRoute {@link #parentRoute}
   * @param route {@link #route}
   */
  protected Descriptor(Map parentRoute = null, Map route = null) {
    this.parentRoute = parentRoute
    this.route = route
  }

  /**
   * Create basic breadcrumb descriptor
   * @param parentRoute {@link #parentRoute}
   * @param route {@link #route}
   * @return basic descriptor instance
   */
  public static Descriptor get(Map parentRoute, Map route = null) {
    new Descriptor(parentRoute, route)
  }

  /**
   * Creates root breadcrumb descriptor. It means handler stops looking for parent.
   * @return root descriptor instance
   */
  public static Descriptor root() {
    new RootDescriptor()
  }

  /**
   * Creates empty breadcrumb descriptor. It means node does need a breadcrumb.
   * @return empty descriptor instance
   */
  public static Descriptor empty() {
    new EmptyDescriptor()
  }

  /**
   * Creates auto breadcrumb descriptor. It determines parent route from request params.
   * @param params request params
   * @return auto descriptor instance
   */
  public static Descriptor auto(Map params) {
    new AutoDescriptor(params)
  }

  /**
   * Sets action to produce title of the breadcrumb. Takes Map (params scope) as a parameter
   * @param strategy {@link #composeTitle}
   * @return this
   */
  public Descriptor composeTitleStrategy(Closure<String> strategy) {
    composeTitle = strategy
    return this
  }

  /**
   * <p>Set a rule to modify parent route according to the runtime data.</p>
   * <p>It takes two arguments:</p>
   * <ul>
   *   <li>Map route - map with current parent route value</li>
   *   <li>Map params - map with request params</li>
   * </ul>
   * <p>Returns modified route</p>
   *
   * @param strategy {@link #modifyParentRoute}
   * @return this
   */
  public Descriptor modifyParentRouteStrategy(Closure<Map> strategy) {
    modifyParentRoute = strategy
    return this
  }

  /**
   * @return {@code true} if this descriptor is Root descriptor, {@code false} otherwise
   */
  public boolean isRoot() {
    false
  }

  /**
   * @return {@code true} if this descriptor is Empty descriptor, {@code false} otherwise
   */
  public boolean isEmpty() {
    false
  }

  /**
   * Converts hyphenated name to lowerCamelCase one
   * @param input hyphenated name
   * @return name converted to lowerCamelCase
   */
  public static String deHyphenate(String input) {
    if (!input) return ''


    StringBuilder builder = new StringBuilder()
    def length = input.length()
    char hyphen = '-' as char

    for (int i = 0; i < length; i++) {
      char c = input.charAt(i)
      if (c != hyphen) {
        builder.append(c)
        continue
      }

      i++
      builder.append(Character.toUpperCase(input.charAt(i)))
    }

    builder.toString()
  }
}
