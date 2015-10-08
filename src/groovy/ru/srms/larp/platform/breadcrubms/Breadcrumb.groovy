package ru.srms.larp.platform.breadcrubms

/**
 * POJO for a breadcrumb
 *
 * @author Treble Snake
 */
public class Breadcrumb {
  String title
  String url

  Breadcrumb(String title, def url) {
    this.title = title
    this.url = url
  }
}
