package ru.srms.larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.request.CharacterRequest
import ru.srms.larp.platform.game.character.request.RequestFormField
import ru.srms.larp.platform.game.character.request.RequestStatus

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


  @PreAuthorize("!#game.confidential || hasPermission(#game, read)")
  def createRequest(Game game) {
    new CharacterRequest(game: game, user: springSecurityService.currentUser)
  }

  @PreAuthorize("(!#request.game.confidential || hasPermission(#request.game, read)) and \
      (isFullyAuthenticated() and principal.username == #request.user.username)")
  def editRequest(CharacterRequest request) {
    request
  }

  @Transactional
  @PreAuthorize("!#request.game.confidential || hasPermission(#request.game, read)")
  def saveRequest(CharacterRequest request) {
    request.save()
  }

  @PreAuthorize("(!#request.game.confidential || hasPermission(#request.game, read)) and \
      (isFullyAuthenticated() and principal.username == #request.user.username)")
  @Transactional
  def updateRequest(CharacterRequest request) {
    request.save()
  }

  def findForCurrentUser(Game game) {
    CharacterRequest.findAllByGameAndUser(game, springSecurityService.currentUser)
  }

  @PreAuthorize("((!#request.game.confidential || hasPermission(#request.game, read)) and \
        isFullyAuthenticated() and principal.username == #request.user.username) \
                        || hasPermission(#request.game, admin)")
  def showRequest(CharacterRequest request) {
    request
  }

  @Transactional
  @PreAuthorize("hasPermission(#request.game, admin)")
  def changeStatus(CharacterRequest request) {
    request.save()
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def listRequests(Game game, Boolean all, Map paginator) {
    CharacterRequest.findAllByGameAndStatusNotInList(game, excludeStatuses(all), paginator)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def countRequests(Game game, Boolean all) {
    CharacterRequest.countByGameAndStatusNotInList(game, excludeStatuses(all))
  }

  private def excludeStatuses(Boolean all) {
    all ? [RequestStatus.DRAFT] :
        [RequestStatus.DRAFT, RequestStatus.DONE, RequestStatus.DECLINED]
  }


}
