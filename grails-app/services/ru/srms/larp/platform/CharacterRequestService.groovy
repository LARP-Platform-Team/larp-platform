package ru.srms.larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.request.CharacterRequest
import ru.srms.larp.platform.game.character.request.FormFieldValue
import ru.srms.larp.platform.game.character.request.RequestFormField

@Transactional(readOnly = true)
class CharacterRequestService {

  SpringSecurityService springSecurityService

  @PreAuthorize("hasPermission(#parent.extractGame(), admin)")
  def listFields(EntityWrapper parent, Map pagination = null) {
    RequestFormField.findAllByParent(parent, pagination)
  }

  @PreAuthorize("hasPermission(#parent.extractGame(), admin)")
  def countFields(EntityWrapper parent) {
    RequestFormField.countByParent(parent)
  }

  @PreAuthorize("hasPermission(#parent.extractGame(), admin)")
  def createField(def parent, EntityWrapper parentWrapper = null) {
    new RequestFormField(parent: parentWrapper)
  }

  @PreAuthorize("hasPermission(#field.extractGame(), admin)")
  def editField(RequestFormField field) {
    field
  }

  @Transactional
  @PreAuthorize("hasPermission(#field.extractGame(), admin)")
  def saveField(RequestFormField field) {
    field.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#field.extractGame(), admin)")
  def deleteField(RequestFormField field) {
    field.delete()
  }


  def createRequest(Game game, Map params) {
    composeRequest(game, params)
  }

  @Transactional
  def saveRequest(CharacterRequest request) {
    request.save()
  }

  CharacterRequest composeRequest(Game game, Map inputParams) {
    def request = new CharacterRequest(game: game, user: springSecurityService.currentUser);
    RequestFormField.findAllByParent(game.wrapper).each {
      if(!it.type.isInput()) return;

      def value = new FormFieldValue(field: it)
      String fieldId = "request_from_field_${it.id}"
      value.value = it.type.transform(inputParams[fieldId])
      request.addToValues(value)
    }

    return request
  }
}
