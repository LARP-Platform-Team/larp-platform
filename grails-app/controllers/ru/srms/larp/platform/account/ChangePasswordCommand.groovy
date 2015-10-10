package ru.srms.larp.platform.account

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.Validateable
import ru.srms.larp.platform.sec.ui.RegisterController

/**
 *
 * <p>Created 06.08.15</p>
 * @author kblokhin
 */
@Validateable
class ChangePasswordCommand {
  String username
  String oldPassword
  String newPassword
  String confirmPassword

  SpringSecurityService springSecurityService
  def passwordEncoder

  static constraints = {
    oldPassword blank: false, validator: oldPasswordValidator
    newPassword blank: false, validator: RegisterController.passwordValidator
    confirmPassword blank: false, validator: {val, obj -> val == obj.newPassword}
  }

  // TODO maybe there is a better way
  static final oldPasswordValidator = { String password, ChangePasswordCommand command ->
    if(!command.passwordEncoder)
      return false;

    return command.passwordEncoder.isPasswordValid(command.springSecurityService.currentUser.password, password, null)
  }
}
