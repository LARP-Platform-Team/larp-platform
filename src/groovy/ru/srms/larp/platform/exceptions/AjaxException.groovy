package ru.srms.larp.platform.exceptions

/**
 *
 * <p>Created 29.07.15</p>
 * @author kblokhin
 */
class AjaxException extends Exception {
  AjaxException(Throwable var1) {
    super(var1)
  }

  AjaxException(String var1) {
    super(var1)
  }

  AjaxException(String var1, Throwable var2) {
    super(var1, var2)
  }

  AjaxException(String var1, Throwable var2, boolean var3, boolean var4) {
    super(var1, var2, var3, var4)
  }
}
