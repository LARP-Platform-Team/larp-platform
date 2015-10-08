package ru.srms.larp.platform.game.chip

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ChipService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class GameChipController extends BaseModuleController {

  ChipService chipService

  @Override
  public Map getBreadcrumbDescriptors() {
    [index: Descriptor.root()]
  }

  def index() {
    withModule {
      respond chipService.list(params.game, paginator(), params.filter),
          model: [itemsQty: chipService.count(params.game, params.filter)]
    }
  }

  def create() {
    withModule {
      respond chipService.create(params.game)
    }
  }

  def edit(GameChip chip) {
    withModule {
      respond chipService.edit(chip)
    }
  }

  @Transactional
  def save(GameChip chip) {
    withModule {
      chip.notes = cleanHtml(chip.notes, 'simple-rich-text')
      if (validateData(chip, 'create')) {
        chipService.save(chip)
        respondChange('Чип успешно создан', CREATED)
      }
    }
  }

  @Transactional
  def update(GameChip chip) {
    withModule {
      chip.notes = cleanHtml(chip.notes, 'simple-rich-text')
      if (validateData(chip, 'edit')) {
        chipService.save(chip)
        respondChange('Чип успешно обновлен', OK)
      }
    }
  }

  @Transactional
  def delete(GameChip chip) {
    withModule {
      if (validateData(chip)) {
        chipService.delete(chip)
        respondChange('Чип удален', NO_CONTENT)
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.CHIP_DATABASE
  }
}
