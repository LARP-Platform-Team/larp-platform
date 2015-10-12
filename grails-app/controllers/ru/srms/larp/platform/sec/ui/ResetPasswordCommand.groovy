package ru.srms.larp.platform.sec.ui

import grails.validation.Validateable

@Validateable
class ResetPasswordCommand {
  String username
  String token
  String password
  String password2

  static constraints = {
    token validator: RegisterController.restorePasswordTokenValidator
    password blank: false, validator: RegisterController.passwordValidator
    password2 validator: RegisterController.password2Validator
  }
}
