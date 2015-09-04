package ru.srms.larp.platform.account

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.UserService
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.sec.SpringUser

import static org.springframework.http.HttpStatus.OK

@Secured(['permitAll'])
@Transactional(readOnly = true)
class AccountController extends BaseController {

  static defaultAction = "show"
  static allowedMethods = [update: "POST"]

  UserService userService

  def show(SpringUser user) {
    def masters = Game.where {masters { id == user.id }}
    def plays = Game.where { characters { player.id == user.id } }
    respond user, model: [masters: masters, plays: plays]
  }

  @Transactional
  def update() {
    def user = SpringUser.get(params.id)
    user.properties['email', 'name'] = params
    user.validate()
    if (validateData(user, 'show')) {
      userService.save(user)
      respondChange('Данные успешно обновлены', OK, user)
    }
  }

  @Transactional
  def changePassword(ChangePasswordCommand passwordCommand) {
    def user = SpringUser.get(params.id)
    // TODO check if null

    passwordCommand.username = user.username
    if(!passwordCommand.validate()) {
      respond user, model: [changePassword: passwordCommand], view: 'show'
      return
    }

    user.password = passwordCommand.newPassword
    userService.save(user)
    respondChange('Пароль успешно изменен', OK, user)
  }

  @Override
  protected Map redirectParams() {
    def attrs = super.redirectParams()
    attrs.action = defaultAction
    attrs.id = params.id
    return attrs
  }

}
