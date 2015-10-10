package ru.srms.larp.platform.sec.ui

import grails.validation.Validateable
import ru.srms.larp.platform.sec.SpringUser

@Validateable
class RegisterCommand {

  String name
  String username
  String email
  String password
  String password2

  static constraints = {
    username maxSize: 64, blank: false, matches: RegisterController.USERNAME_REGEX,
        validator: { value, command ->
          if (value && SpringUser.findByUsername(value))
            return 'registerCommand.username.unique'
        }
    name maxSize: 64, blank: false, matches: RegisterController.NAME_REGEX,
        validator: { value, command ->
          if (value && SpringUser.findByName(value))
              return 'registerCommand.name.unique'
        }
    email blank: false, email: true, validator: { value, command ->
      if (value && SpringUser.findByEmail(value))
        return 'registerCommand.email.unique'
    }
    password blank: false, validator: RegisterController.passwordValidator
    password2 validator: RegisterController.password2Validator
  }
}
