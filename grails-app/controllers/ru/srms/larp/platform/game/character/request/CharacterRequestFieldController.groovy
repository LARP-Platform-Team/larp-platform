package ru.srms.larp.platform.game.character.request

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.CharacterRequestService
import ru.srms.larp.platform.EntityWrapper
import ru.srms.larp.platform.GameRoleService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.domain.Wrapped
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.roles.GameRole

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class CharacterRequestFieldController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]

  CharacterRequestService characterRequestService
  GameRoleService gameRoleService


  @Override
  public Map getBreadcrumbDescriptors() {
    [
        index               : Descriptor.get([controller: 'characterRequest', action: 'index'])
            .modifyParentRouteStrategy { Map route, Map params ->
          def item = RequestFormField.get(params.get('id') as Long)
          if (params.role?.id || item?.parent?.entity instanceof GameRole) {
            return [controller: 'gameRole', action: 'index']
          }
          route
        },
        (Descriptor.DEFAULT_KEY): Descriptor.get([controller: 'characterRequestField', action: 'index'])
            .modifyParentRouteStrategy { Map route, Map params ->
          def item = RequestFormField.get(params.get('id') as Long)
          if (params.role?.id || item?.parent?.entity instanceof GameRole) {
            if (!route.params) route.params = [:]
            route.params.put('role.id', params.role?.id ?: item.parent.entityId)
          }
          route
        }
    ]
  }

  def index() {
    withModule {
      def parentWrapper = getParentWrapper()
      def items = parentWrapper ? characterRequestService.listFields(parentWrapper, paginator(100)) : []
      def itemsQty = parentWrapper ? characterRequestService.countFields(parentWrapper) : 0
      respond items, model: [itemsQty: itemsQty]
    }
  }

  def create() {
    withModule {
      def parent = getParent()
      def parentWrapper = getParentWrapper(parent)
      respond characterRequestService.createField(parent, parentWrapper)
    }
  }

  def edit(RequestFormField field) {
    withModule {
      respond characterRequestService.editField(field)
    }
  }

  @Transactional
  def save(RequestFormField field) {
    withModule {
      def parent = getParent()
      field.parent = parent ? EntityWrapper.wrap(parent) : null;
      if (validateData(field, 'create')) {
        characterRequestService.saveField(field)
        updateRoleAvailability(field.parent)
        respondChange('Поле успешно создано', CREATED, redirectRoute())
      }
    }
  }

  @Transactional
  def update(RequestFormField field) {
    withModule {
      if (validateData(field, 'edit')) {
        characterRequestService.saveField(field)
        respondChange('Поле обновлено', OK, redirectRoute(field))
      }
    }
  }

  @Transactional
  def delete(RequestFormField field) {
    withModule {
      if (validateData(field)) {
        characterRequestService.deleteField(field)
        updateRoleAvailability(field.parent)
        respondChange('Поле удалено', NO_CONTENT, redirectRoute(field))
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.REQUEST_FORM
  }

  @Transactional
  private def updateRoleAvailability(EntityWrapper wrapper) {
    def parent = wrapper.entity
    if (!(parent instanceof GameRole))
      return;

    GameRole parentRole = parent as GameRole
    def fieldsQty = RequestFormField.countByParent(wrapper)
    if (fieldsQty == 1 || fieldsQty == 0) {
      parentRole.requestAvailable = fieldsQty == 1
      gameRoleService.save(parentRole)
    }
  }

  private def redirectRoute(RequestFormField instance = null) {
    def roleId = null
    if (params.role?.id)
      roleId = params.role?.id
    else if(instance) {
      def parent = instance.parent?.entity
      if (parent && parent instanceof GameRole)
        roleId = parent.id
    }

    return roleId ?
        [params: ['role.id': roleId]] :
        null
  }

  private def getParent() {
    return params.role?.id ? GameRole.get(params.role.id) : params.game
  }

  private EntityWrapper getParentWrapper(def parent = null) {
    parent = parent ?: getParent()
    if (parent && parent instanceof Wrapped) {
      return ((Wrapped) parent).wrapper
    }
    return null
  }
}
