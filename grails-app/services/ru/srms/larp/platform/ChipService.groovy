package ru.srms.larp.platform

import grails.gorm.DetachedCriteria
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.chip.GameChip

@Transactional(readOnly = true)
class ChipService {

  @PreAuthorize("hasPermission(#game, admin)")
  def list(Game game, Map pagination, GrailsParameterMap filter) {
    if(!filter) return GameChip.findAllByGame(game, pagination)
    buildCriteria(filter).list(pagination)
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def count(Game game, GrailsParameterMap filter) {
    if(!filter) return GameChip.countByGame(game)
    buildCriteria(filter).count()
  }

  private DetachedCriteria buildCriteria(GrailsParameterMap filter) {
    new DetachedCriteria(GameChip).build {
      or {
        if (filter.title) ilike('title', "%${filter.title}%")
        if (filter.code) ilike('code', "%${filter.code}%")
        if (filter.owner) inList('owner.id', filter.list('owner').collect { Long.valueOf(it) })
      }
    }
  }

  @PreAuthorize("hasPermission(#game, admin)")
  def create(Game game) {
    new GameChip(game: game)
  }

  @PreAuthorize("hasPermission(#chip.game, admin)")
  def edit(GameChip chip) { chip }

  @Transactional
  @PreAuthorize("hasPermission(#chip.game, admin)")
  def save(GameChip chip) {
    chip.save()
  }

  @Transactional
  @PreAuthorize("hasPermission(#chip.game, admin)")
  def delete(GameChip chip) {
    chip.delete()
  }

}
