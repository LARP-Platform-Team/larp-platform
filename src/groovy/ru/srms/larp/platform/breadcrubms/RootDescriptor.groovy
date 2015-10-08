package ru.srms.larp.platform.breadcrubms

/**
 * Descriptor of the root breadcrumb (no further propagating is needed)
 *
 * @author Treble Snake
 */
class RootDescriptor extends Descriptor {

  RootDescriptor() {
  }

  @Override
  boolean isRoot() {
    true
  }
}
