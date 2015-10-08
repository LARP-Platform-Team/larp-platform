package ru.srms.larp.platform.breadcrubms

/**
 * Null-object descriptor. Indicates that no breadcrumb is needed.
 *
 * @author Treble Snake
 */
class EmptyDescriptor extends Descriptor {
  protected EmptyDescriptor() {
  }

  @Override
  boolean isEmpty() {
    true
  }
}
