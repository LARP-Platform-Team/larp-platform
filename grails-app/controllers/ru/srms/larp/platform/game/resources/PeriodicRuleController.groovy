package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class PeriodicRuleController extends BaseModuleController {

  ResourceService resourceService

  static allowedMethods = [save: "POST", update: "POST"]

  def create() {
    withModule {
      if (!ResourceInstance.exists(params.target.id))
        throw new Exception("Неверный ресурс")
      def target = ResourceInstance.get(params.target.id)
      respond resourceService.createPeriodicRule(target)
    }
  }

  def edit(PeriodicRule rule) {
    withModule {
      respond resourceService.editPeriodicRule(rule)
    }
  }

  @Transactional
  def save(PeriodicRule rule) {
    withModule {
      rule.fireDays = readWeekDays()
      if (validateData(rule, 'create')) {
        resourceService.savePeriodicRule(rule)
        respondChange('Правило периодического изменения успешно создано', CREATED, rule)
      }
    }
  }

  @Transactional
  def update(PeriodicRule rule) {
    withModule {
      rule.fireDays = readWeekDays()
      if (validateData(rule, 'edit')) {
        resourceService.savePeriodicRule(rule)
        respondChange('Правило периодического изменения обновлено', OK, rule)
      }
    }
  }

  private def readWeekDays() {
    PeriodicRule.WeekDays.values()
        .findAll { params[it.toString()] }
  }

  @Transactional
  def delete(PeriodicRule rule) {
    withModule {
      if (validateData(rule)) {
        // save feed id to params for redirect
        params.target = [:]
        params.target.id = rule.target.id

        resourceService.deletePeriodicRule(rule)
        respondChange('Правило периодического изменения удалено', NO_CONTENT, null, rule.id)
      }
    }
  }

  @Override
  protected Map redirectParams() {
    def attrs = super.redirectParams()
    attrs.controller = 'ResourceInstance'
    attrs.action = 'edit'
    attrs.id = params.target.id
    return attrs
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.RESOURCES
  }
}
