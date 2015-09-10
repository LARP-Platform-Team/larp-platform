package ru.srms.larp.platform

import org.springframework.http.HttpStatus
import ru.srms.larp.platform.exceptions.AjaxException

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

abstract class BaseController {

  /**
   * Computes maximum of entries per page for pagination
   * @param max max parameter coming from request
   * @return maximum of entries
   */
  protected int pageMax(Integer max) {
    Math.min(max ?: 10, 100)
  }

  /**
   * Composes map with pagination options based on request parameters.<br/>
   * Also sets up params.max value.
   * @return map with offset and max entries for pagination
   */
  protected Map paginator(Integer max = null) {
    params.max = pageMax(params.int('max') ?: max)
    [offset: params.offset ?: 0, max: params.max]
  }

  /**
   * Validates input data for domain object and make redirects if instance is not found or
   * contains errors
   * TODO find common base class for domain objects
   * @param object domain instance object
   * @param view view to redirect to
   * @return {@code true} if validation is successful, {@code false} otherwise
   */
  protected boolean validateData(def object, String view) {
    if (object == null) {
      notFound()
      return false
    }

    object.validate()
    if (view && object.hasErrors()) {
      respond object.getErrors(), view: view
      return false
    }

    return true
  }

  /**
   * Validates input data for domain object and make redirects if instance is not found
   * @param object domain instance object
   * @return {@code true} if validation is successful, {@code false} otherwise
   */
  protected boolean validateData(def object) {
    return validateData(object, null)
  }

  /**
   * @return i18n message code for domain class label
   */

  /**
   * Make appropriate respond if instance was not found
   */
  protected void notFound() {
//        respondChange(message(code: 'default.not.found.message').toString(), NOT_FOUND, null, params.id)
    render status: 404
  }

  /**
   * TODO accept text instead of message
   * Make appropriate respond after instance was somehow changed
   * @param message code for information message relevant to performed action
   * @param status http status code
   * @param object domain class instance
   * @param id domain class instance id
   * @return The result of the "request.withFormat" closure call
   */
  // TODO remove object and id args
  protected def respondChange(String message, HttpStatus status,
                              def object, def id = null, def route = null) {

    flash.success = message;
    def map = route ?
        redirectParams(route.action, route.id, route.controller)
        : redirectParams()
    map.status = status
    redirect map

    // TODO если "удалить" делать ссылкой, то редиректа не происходит. разобраться.
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: message, args: [message(code: labelCode()), id ?: object?.id])
//                redirect action: 'index', params: [gameAlias: params.gameAlias], method: "GET"
//            }
//            '*' {
//                if (object)
//                    respond object, [status: status]
//                else
//                    render status: status
//            }
//        }
  }

  protected Map redirectParams() {
    return redirectParams('index', null, null)
  }
  /**
   * May be overriden for custom redirect rules
   * @return map of redirect params
   */
  protected Map redirectParams(String action, def id, String controller) {
    // TODO retrieve defaultAction somehow instead of just "index"
    def defaults = [action: action, params: [:], method: "GET"]
    if (id) defaults.id = id
    if (controller) defaults.controller = controller
    if (params.gameAlias) defaults.params.gameAlias = params.gameAlias
    if (params.charAlias) defaults.params.charAlias = params.charAlias
    return defaults
  }

  /**
   * Wrapper for ajax actions (renders errors in appropriate format)
   * @param action action to perform
   */
  protected def doAjax(Closure action) {
    try {
      action()
    } catch (AjaxException e) {
      renderAjaxError(ui.message([type: 'error'], e.getMessage()).toString())
    }
    catch (Exception other) {
      renderAjaxError("Что-то пошло не так: ${other.class.simpleName} (${other.message})")
    }
  }

  // TODO разобраться, почему он все время пытается вызвать эти методы при возникновении ошибок (из-за чего их пришлось сделать public)
  /**
   * Renders an error message for ajax action from exception
   * @param message message it is
   */
  public def renderAjaxError(String message) {
    response.status = INTERNAL_SERVER_ERROR.value()
    render message
  }
}
