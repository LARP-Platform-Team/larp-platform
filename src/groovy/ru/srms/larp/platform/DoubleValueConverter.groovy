package ru.srms.larp.platform

import org.grails.databinding.converters.ValueConverter

/**
 * Found here // found here http://stackoverflow.com/questions/14877602/grails-databinding-with-decimal-delimiter
 * <p>Created 04.09.15</p>
 */
class DoubleValueConverter implements ValueConverter {

  public DoubleValueConverter() {
  }

  boolean canConvert(value) {
    value instanceof Double
  }

  def convert(value) {
    //In my case returning the same value did the trick but you can define
    //custom code that takes care about comma and point delimiter...
    return value
  }

  Class<?> getTargetType() {
    return Double.class
  }
}
