package ru.srms.larp.platform.game.mail

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class MailBoxController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST", addAddressBookEntry: "POST"]
  MailboxService mailboxService

  @Override
  public Map getBreadcrumbDescriptors() {
    def addressBookDescriptor = Descriptor.get([controller: 'mailBox', action: 'show'])
        .modifyParentRouteStrategy { Map route, Map params -> route.id = params.id; route }
    [
        index      : Descriptor.root(),
        show       : Descriptor.root(),
        addressBook: addressBookDescriptor,
        addSavedAddress: addressBookDescriptor,
        deleteSavedAddress: addressBookDescriptor
    ]
  }

  def index() {
    withModule {
      respond mailboxService.all(params.game, paginator()),
          model: [itemsCount: mailboxService.countAll(params.game)]
    }
  }

  def show(MailBox instance) {
    withModule {
      respond mailboxService.show(instance), model: [
          letters: mailboxService.letters(instance)]
    }
  }

  def create() {
    withModule {
      respond mailboxService.create(params.game)
    }
  }

  def edit(MailBox instance) {
    withModule {
      respond mailboxService.edit(instance)
    }
  }

  def addressBook(MailBox box) {
    withModule {
      respond mailboxService.readAddressBook(box)
    }
  }

  @Transactional
  def save(MailBox instance) {
    withModule {
      if (validateData(instance, 'create')) {
        mailboxService.save(instance)
        respondChange('Почтовый ящик успешно создан', CREATED)
      }
    }
  }

  @Transactional
  def update() {
    withModule {
      def instance = MailBox.get(params.id)
      def oldOwner = instance.owner
      instance.properties = params
      if (validateData(instance, 'edit')) {
        mailboxService.save(instance, oldOwner)
        respondChange('Почтовый ящик обновлен', OK)
      }
    }
  }

  @Transactional
  def delete(MailBox instance) {
    withModule {
      if (validateData(instance)) {
        mailboxService.delete(instance)
        respondChange('Почтовый ящик удален', NO_CONTENT)
      }
    }
  }

  @Transactional
  def addAddressBookEntry(MailBox box) {
    doAjax {
      withModule {
        def result = [success: false]

        if (params.target.id && MailBox.exists(params.target.id)) {
          result = [success: mailboxService.addAddress(box, MailBox.get(params.target.id))]
        }
        render result as JSON
      }
    }
  }

  @Transactional
  def deleteSavedAddress(MailBox box) {
    withModule {
      def instance = params.entry.id ? AddressBookEntry.get(params.entry.id) : null
      if (validateData(instance, 'addressBook')) {
        mailboxService.deleteSavedAddress(instance)
        respondChange('Запись из адресной книги удалена', NO_CONTENT, [action: 'addressBook', id: box.id])
      }
    }
  }

  @Transactional
  def addSavedAddress(MailBox box) {
    withModule {
      def command = new AddAddressCommand(newAddress: params.newAddress, game: params.game, currentBox: box)
      command.validate()
      if(command.hasErrors())
      {
        respond box, view: 'addressBook', model: [addAddressCommand: command]
        return
      }

      mailboxService.addAddress(box, MailBox.findByGameAndAddress(command.game, command.newAddress))
      respondChange('Адрес добавлен в адресную книгу', OK, [action: 'addressBook', id: box.id])
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.MAIL
  }
}
