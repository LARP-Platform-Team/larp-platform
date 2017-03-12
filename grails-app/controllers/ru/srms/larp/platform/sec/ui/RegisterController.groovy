package ru.srms.larp.platform.sec.ui

import grails.plugin.mail.MailService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.http.HttpStatus
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.UserService
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.UserActionToken

@Secured(['permitAll'])
@Transactional(readOnly = true)
class RegisterController extends BaseController {

  UserService userService
  MailService mailService

  static def USERNAME_REGEX = /^[A-Za-z0-9\-\._]+$/
  static def NAME_REGEX = /^[A-Za-zА-Яа-я0-9\-\.\s,_]+$/

  def index() {
    respond new RegisterCommand()
  }

  @Transactional
  def register(RegisterCommand command) {
    if (validateData(command, 'index')) {
      boolean needActivation = grailsApplication.config.grails.larp.platform.user.activation.required
      def user = new SpringUser(
          username: command.username,
          password: command.password,
          email: command.email,
          name: command.name,
          enabled: !needActivation,
          accountExpired: false, accountLocked: false, passwordExpired: false)
      if(!user.validate())
        throw new Exception("RegisterCommand is unreliable")

      def token = userService.register(user, needActivation)
      if (needActivation) {
        def link = generateLink('activate', [token: token])
        mailService.sendMail {
          to command.email
          subject "Регистрация на LARP Platform"
          html g.render(template: 'activateLetter', model: [link: link])
        }
        render view: 'index', model: [emailSent: true]
      }
      else {
        flash.success = 'Вы успешно зарегистрировались и теперь можете войти на сайт!'
        redirect controller: 'login', action: 'auth'
      }
    }
  }

  @Transactional
  def activate(String token) {
    try {
      if (!token) throw new IllegalArgumentException('Неверный код активации')
      def action = UserActionToken.findByTypeAndToken(UserActionToken.ActionType.ACTIVATE, token)
      if (!action)
        throw new IllegalArgumentException('Запись о регистрации не найдена для указанного кода активации')

      def user = SpringUser.findByUsername(action.username)
      if (!user) throw new IllegalArgumentException('Указанный пользователь не найден')

      userService.activate(user, action)
      flash.success = 'Вы успешно активировали свой аккаунт! Теперь вы можете войти на сайт.'
      redirect(controller: 'login', action: 'auth')
    } catch (IllegalArgumentException e) {
      render view: 'activate', model: [error: e.getMessage()]
    }
  }

  @Transactional
  def restorePassword() {
    if (params.containsKey('sendEmail')) {
      try {
        if (params.username && params.username == 'admin')
          throw new IllegalArgumentException('Кыш, галька!')

        def user = SpringUser.findByUsername(params.username)
        if (!user) {
          user = SpringUser.findByEmail(params.username)
          if(!user)
            throw new IllegalArgumentException('Пользователь не найден')
        }

        def token = userService.createPasswordRestoreToken(user.username)
        flash.success = g.message(code: 'spring.security.ui.forgotPassword.sent')

        def link = generateLink('setNewPassword', [token: token])
        mailService.sendMail {
          to user.email
          subject "Восстановление пароля на LARP Platform"
          html g.render(template: 'passwordRestoreLetter', model: [link: link, user: user])
        }
      } catch (IllegalArgumentException e) {
        flash.error = e.getMessage()
      }
    }

    respond HttpStatus.OK
  }

  @Transactional
  def setNewPassword() {
    String token = params.token
    if (!token || !validatePasswordToken(token)) {
      flash.error = 'Неверный или истёкший код восстановления пароля'
      redirect controller: 'game', action: 'index'
      return
    }

    if (params.containsKey('doSetPassword')) {
      def command = new ResetPasswordCommand()
      bindData(command, params, ['username'])
      command.username = UserActionToken.findByToken(command.token).username

      if(validateData(command, 'setNewPassword')) {
        userService.setNewPassword(command)
        flash.success = message(code: 'spring.security.ui.resetPassword.success')
        redirect controller: 'login', action: 'auth'
      }
      return
    }

    respond new ResetPasswordCommand()
  }

  protected boolean validatePasswordToken(String token) {
    def isValid = restorePasswordTokenValidator(token, new ResetPasswordCommand())
    if(isValid == true)
      return true

    return false
  }

  protected String generateLink(String action, linkParams) {
    createLink(absolute: true, controller: 'register', action: action, params: linkParams)
  }

  static final restorePasswordTokenValidator = {String token, command ->
      Calendar calendar = Calendar.getInstance()
      calendar.add(Calendar.HOUR, -1)
      if (UserActionToken.countByTokenAndTypeAndDateCreatedGreaterThan(
          token, UserActionToken.ActionType.RESTORE_PASSWORD, calendar.time) != 1)
        return 'spring.security.ui.resetPassword.badCode'

      def action = UserActionToken.findByToken(token)
      if (SpringUser.countByUsername(action.username) != 1)
        return 'spring.security.ui.forgotPassword.user.notFound'

    return true
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