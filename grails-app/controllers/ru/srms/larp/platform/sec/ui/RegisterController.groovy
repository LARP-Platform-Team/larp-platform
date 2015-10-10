package ru.srms.larp.platform.sec.ui

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.UserService

@Secured(['permitAll'])
class RegisterController extends BaseController {

  UserService userService

  static def USERNAME_REGEX = /^[A-Za-z0-9\-\._]+$/
  static def NAME_REGEX = /^[A-Za-zА-Яа-я0-9\-\.\s,_]+$/

  def index() {
    respond new RegisterCommand()
  }

  def register(RegisterCommand command) {
    if (validateData(command, 'index')) {
      userService.register(command)
      render view: 'index', model: [emailSent: true]
    }
  }

  static final passwordValidator = { String password, command ->
    if (command.username && command.username.equals(password)) {
      return 'command.password.error.username'
    }

    if (!checkPasswordMinLength(password, command) ||
        !checkPasswordMaxLength(password, command) ||
        !checkPasswordRegex(password, command)) {
      return 'command.password.error.strength'
    }
  }

  static boolean checkPasswordMinLength(String password, command) {
    def conf = SpringSecurityUtils.securityConfig
    int minLength = conf.ui.password.minLength instanceof Number ? conf.ui.password.minLength : 8
    password && password.length() >= minLength
  }

  static boolean checkPasswordMaxLength(String password, command) {
    def conf = SpringSecurityUtils.securityConfig
    int maxLength = conf.ui.password.maxLength instanceof Number ? conf.ui.password.maxLength : 64
    password && password.length() <= maxLength
  }

  static boolean checkPasswordRegex(String password, command) {
    def conf = SpringSecurityUtils.securityConfig
    String passValidationRegex = conf.ui.password.validationRegex ?:
        '^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'
    password && password.matches(passValidationRegex)
  }

  static final password2Validator = { value, command ->
    if (command.password != command.password2) {
      return 'command.password2.error.mismatch'
    }
  }
}