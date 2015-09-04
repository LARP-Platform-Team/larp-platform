package ru.srms.larp.platform.sec.ui

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.plugin.springsecurity.ui.RegistrationCode

/**
 * TODO temp
 */
class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {

  @Override
  def index() {
    def copy = [:] + (flash.chainedParams ?: [:])
    copy.remove 'controller'
    copy.remove 'action'
    [command: new RegisterCommand(copy)]
  }

  @Override
  def register(grails.plugin.springsecurity.ui.RegisterCommand oldCommand) {

    RegisterCommand command = new RegisterCommand()
    bindData(command, params)

    if(!params.containsKey('doRegister')) {
      render view: 'index', model: [command: new RegisterCommand()]
      return
    }

    if (command.hasErrors()) {
      render view: 'index', model: [command: command]
      return
    }

    String salt = saltSource instanceof NullSaltSource ? null : command.username
    // TODO makle enabled: true only for dev enviroment
    def user = lookupUserClass().newInstance(email: command.email, username: command.username,
        name: command.name, accountLocked: false, enabled: true)

    RegistrationCode registrationCode = springSecurityUiService.register(user, command.password, salt)
    if (registrationCode == null || registrationCode.hasErrors()) {
      // null means problem creating the user
      flash.error = message(code: 'spring.security.ui.register.miscError')
      flash.chainedParams = params
      redirect action: 'index'
      return
    }

    String url = generateLink('verifyRegistration', [t: registrationCode.token])

    def conf = SpringSecurityUtils.securityConfig
    def body = conf.ui.register.emailBody
    if (body.contains('$')) {
      body = evaluate(body, [user: user, url: url])
    }
    mailService.sendMail {
      to command.email
      from conf.ui.register.emailFrom
      subject conf.ui.register.emailSubject
      html body.toString()
    }

    render view: 'index', model: [emailSent: true]
  }
}

class RegisterCommand extends grails.plugin.springsecurity.ui.RegisterCommand {

  String name

  // TODO вынести и использовать валидатор тут и в SpringUser
  static constraints = {
    username blank: false, matches: /^[A-Za-z0-9\-\.]+$/, validator: { value, command ->
      if (value) {
        def User = command.grailsApplication.getDomainClass(
            SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
        if (User.findByUsername(value)) {
          return 'registerCommand.username.unique'
        }
      }
    }
    name blank: false, matches: /^[A-Za-zА-Яа-я0-9\-\.,]+$/, validator: { value, command ->
      if (value) {
        def User = command.grailsApplication.getDomainClass(
            SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
        if (User.findByName(value)) {
          return 'registerCommand.username.unique'
        }
      }
    }
    email blank: false, email: true
    password blank: false, validator: RegisterController.passwordValidator
    password2 validator: RegisterController.password2Validator
  }
}