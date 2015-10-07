package ru.srms.larp.platform.account

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.UserService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.sec.SpringUser

import static org.springframework.http.HttpStatus.OK

@Secured(['permitAll'])
@Transactional(readOnly = true)
class AccountController extends BaseController {

  static defaultAction = "show"
  static allowedMethods = [update: "POST"]

  UserService userService
  def springSecurityService

  @Override
  Map getBreadcrumbDescriptors() {
    [(Descriptor.DEFAULT_KEY): Descriptor.root()]
  }

  def show(SpringUser user) {
    def masters = Game.where { masters { id == user.id } }
    def plays = Game.where { characters { player.id == user.id } || invitedPlayers { id == user.id} }
    respond user, model: [masters: masters, plays: plays]
  }

  @Transactional
  def update() {
    def user = SpringUser.get(params.id)
    user.properties['email', 'name'] = params
    if (validateData(user, 'show')) {
      userService.save(user)
      springSecurityService.reauthenticate user.username
      respondChange('Данные успешно обновлены', OK, [action: defaultAction, id: user.id])
    }
  }

  @Transactional
  def changePassword(ChangePasswordCommand passwordCommand) {
    def user = SpringUser.get(params.id)
    passwordCommand.username = user.username
    if(!passwordCommand.validate()) {
      respond user, model: [changePassword: passwordCommand], view: 'show'
      return
    }

    user.password = passwordCommand.newPassword
    userService.save(user)
    springSecurityService.reauthenticate user.username
    respondChange('Пароль успешно изменен', OK, [action: defaultAction, id: user.id])
  }

}
