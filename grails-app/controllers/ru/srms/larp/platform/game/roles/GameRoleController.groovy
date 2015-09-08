package ru.srms.larp.platform.game.roles

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.GameAclService
import ru.srms.larp.platform.GameRoleService
import ru.srms.larp.platform.exceptions.AjaxException
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.permissions.GamePermission

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class GameRoleController extends BaseController {

  static allowedMethods = [save: "POST", update: "POST", addToChar: "POST", removeFromChar: "POST"]

  GameRoleService gameRoleService
  GameAclService gameAclService

  def index() {
    respond gameRoleService.list(params.game, null, paginator()),
        model: [rolesCount: gameRoleService.count(params.game, null)]
  }

  def create() {
    respond gameRoleService.create(params.game,
        params.parent ? GameRole.get(params.parent) : null)
  }

  def edit(GameRole role) {
    respond gameRoleService.edit(role)
  }

  @Transactional
  def save(GameRole role) {
    if (validateData(role, 'create')) {
      gameRoleService.save(role)
      respondChange('Роль успешно добавлена', CREATED, role)
    }
  }

  @Transactional
  def update(GameRole role) {
    if (validateData(role, 'edit')) {
      gameRoleService.save(role)
      respondChange('Роль обновлена', OK, role)
    }
  }

  @Transactional
  def delete(GameRole role) {
    if (validateData(role)) {
      gameRoleService.delete(role)
      respondChange('Роль удалена', NO_CONTENT, null, role.id)
    }
  }

  def config(GameRole role) {
    respond role, model: [
        acls: gameAclService.getAclMatrix(role)
    ]
  }

  @Transactional
  def setPermission() {
    doAjax {
      try {
        def role = GameRole.get(params.id)
        if (!role) throw new AjaxException("Неверная роль")
        def permission = GamePermission.valueOf(params.permission)
        def clazz = this.class.classLoader.loadClass(params.clazz)
        boolean value = gameAclService.setPermission(role, clazz, params.long("itemId"), permission)

        render template: 'permission', model: [value: value]
      } catch (Exception e) {
        if (e instanceof AjaxException)
          throw e

        throw new AjaxException(e.message, e)
      }
    }
  }

  @Transactional
  def addToChar(GameRole gameRole) {
    doAjax {
      Long id = params.character?.id as Long
      if (!id || !GameCharacter.exists(id))
        throw new AjaxException("Указан неверный персонаж!")

      if (gameRole.characters.find { itm -> itm.id == id })
        throw new AjaxException("Указанный персонаж уже назначен на эту роль!")

      gameRoleService.add(gameRole, GameCharacter.get(id))

      render template: 'characters', model: [characters: gameRole.characters]
    }
  }

  @Transactional
  def removeFromChar(GameRole gameRole) {
    doAjax {
      Long id = params.characterId as Long
      if (!id) throw new AjaxException("Указан неверный персонаж!")
      gameRoleService.remove(gameRole, GameCharacter.get(id))

      render template: 'characters', model: [characters: gameRole.characters]
    }
  }


}
