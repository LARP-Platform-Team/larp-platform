package ru.srms.larp.platform.game.resources

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.ResourceService
import ru.srms.larp.platform.breadcrubms.Descriptor
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
@Transactional(readOnly = true)
class ResourcePeriodicRuleController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]

  ResourceService resourceService

  @Override
  Map getBreadcrumbDescriptors() {
    [
        show: Descriptor.root(),
        (Descriptor.DEFAULT_KEY): Descriptor.get([controller: 'resourceInstance', action: 'edit'])
            .modifyParentRouteStrategy { Map route, Map params ->
          def item = ResourcePeriodicRule.get(params.get('id') as Long)
          route.put('id', item?.target?.id ?: params.target?.id)
          route
        }
    ]
  }

  def create() {
    withModule {
      if (!ResourceInstance.exists(params.target.id))
        throw new Exception("Неверный ресурс")
      def target = ResourceInstance.get(params.target.id)
      respond resourceService.createPeriodicRule(target)
    }
  }

  def edit(ResourcePeriodicRule rule) {
    withModule {
      respond resourceService.editPeriodicRule(rule)
    }
  }

  @Transactional
  def save(ResourcePeriodicRule rule) {
    withModule {
      rule.fireDays = readWeekDays()
      rule.validate()
      if (validateData(rule, 'create')) {
        resourceService.savePeriodicRule(rule)
        respondChange('Правило периодического изменения успешно создано', CREATED,
            [controller: 'ResourceInstance', action: 'edit', id: rule.target.id])
      }
    }
  }

  @Transactional
  def update(ResourcePeriodicRule rule) {
    withModule {
      rule.fireDays = readWeekDays()
      rule.validate()
      if (validateData(rule, 'edit')) {
        resourceService.savePeriodicRule(rule)
        respondChange('Правило периодического изменения обновлено', OK,
            [controller: 'ResourceInstance', action: 'edit', id: rule.target.id])
      }
    }
  }

  private def readWeekDays() {
    ResourcePeriodicRule.WeekDays.values()
        .findAll { params[it.toString()] }
  }

  @Transactional
  def delete(ResourcePeriodicRule rule) {
    withModule {
      if (validateData(rule)) {
        resourceService.deletePeriodicRule(rule)
        respondChange('Правило периодического изменения удалено', NO_CONTENT,
            [controller: 'ResourceInstance', action: 'edit', id: rule.target.id])
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.RESOURCES
  }
}
