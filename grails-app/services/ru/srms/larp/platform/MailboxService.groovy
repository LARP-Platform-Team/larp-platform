package ru.srms.larp.platform

import grails.plugin.springsecurity.acl.AclUtilService
import grails.transaction.Transactional
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.game.mail.LetterContent
import ru.srms.larp.platform.game.mail.LetterType
import ru.srms.larp.platform.game.mail.MailBox

import static org.springframework.security.acls.domain.BasePermission.*

@Transactional(readOnly = true)
class MailboxService {

  GameAclService gameAclService
  AclUtilService aclUtilService

//  def list(Game game, GameCharacter character) {
//    MailBox.findAllByGameAndOwner(game, character) + available(game, character)
//  }

  @PostFilter("hasPermission(filterObject, read)")
  def available(Game game) {
    // TODO or is null; reassign bug
    MailBox.findAllByGame(game)
  }

//  @PreAuthorize("hasPermission(#box, read)")
  def letters(MailBox box) {
    Map<LetterType, List<LetterContent>> result = LetterType.values().collectEntries {[(it): []]}
    box.letters.each {
      result.get(it.type).add(it.content)
    }
    result
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def all(Game game, Map pagination) {
    MailBox.findAllByGame(game, pagination)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def countAll(Game game) {
    MailBox.countByGame(game)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  @Transactional(readOnly = true)
  def create(Game game) {
    new MailBox(game: game)
  }

  @PreAuthorize("hasPermission(#mailbox.game, admin)")
  def edit(MailBox mailbox) { mailbox }

  @PreAuthorize("hasPermission(#mailbox.game, admin)")
  @Transactional
  def save(MailBox mailbox, GameCharacter oldOwner = null) {
    boolean insert = mailbox.id == null
    mailbox.save()
    if (insert) gameAclService.createAcl(mailbox)
    changePermissions(mailbox, oldOwner, mailbox.owner)
  }

  private def changePermissions(MailBox mailbox, GameCharacter oldOwner, GameCharacter newOwner) {
    if (oldOwner == newOwner)
      return

    if (oldOwner) {
      aclUtilService.deletePermission(mailbox, oldOwner.authority, READ)
      aclUtilService.deletePermission(mailbox, oldOwner.authority, WRITE)
      aclUtilService.deletePermission(mailbox, oldOwner.authority, CREATE)
      aclUtilService.deletePermission(mailbox, oldOwner.authority, DELETE)
    }

    if (newOwner) {
      aclUtilService.addPermission(mailbox, newOwner.authority, READ)
      aclUtilService.addPermission(mailbox, newOwner.authority, WRITE)
      aclUtilService.addPermission(mailbox, newOwner.authority, CREATE)
      aclUtilService.addPermission(mailbox, newOwner.authority, DELETE)
    }
  }

  @PreAuthorize("hasPermission(#mailbox.game, admin)")
  @Transactional
  def delete(MailBox mailbox) {
    aclUtilService.deleteAcl(mailbox)
    mailbox.delete()
  }


}
