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
    // TODO check if null
    user.properties['email'] = params
    user.validate()
    if (validateData(user, 'show')) {
      userService.saveUser(user)
      respondChange('default.updated.message', OK, user)
    }
  }

  @Override
  protected Map redirectParams() {
    def attrs = super.redirectParams()
    attrs.action = defaultAction
    attrs.id = params.id
    return attrs
  }

  @Override
  protected String labelCode() {
    return "Аккаунт"
  }
}
