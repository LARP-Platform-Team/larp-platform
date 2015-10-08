package ru.srms.larp.platform.breadcrubms
/**
 * Descriptor which determines parent route from request params.
 *
 * @author Treble Snake
 */
class AutoDescriptor extends Descriptor {

  public static final String DEFAULT_ACTION = 'index'

  // TODO determine default action of the controller
  AutoDescriptor(Map route) {
    super([controller: deHyphenate(route.controller), action: DEFAULT_ACTION])
  }
}
