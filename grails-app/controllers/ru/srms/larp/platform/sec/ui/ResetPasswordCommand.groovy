package ru.srms.larp.platform.sec.ui

import ru.srms.larp.platform.sec.SpringUser

/**
 * Created by Treble Snake on 09.10.2015.
 */
class ResetPasswordCommand {
  String username
  String password
  String password2

  static constraints = {
    username validator: {val, obj ->
      SpringUser.countByUsername(val) == 1
    }
    password blank: false, validator: RegisterController.passwordValidator
    password2 validator: RegisterController.password2Validator
  }
}
