package ru.srms.larp.platform

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class EntityWrapper<T> implements InGameStuff {

  def grailsApplication

  String entityClass
  Serializable entityId

  static constraints = {
    entityClass unique: 'entityId'
  }

  static transients = ['entity', 'grailsApplication']

  static mapping = {
    version false
  }

  public static EntityWrapper<T> wrap(T entity)  {
    if(entity == null)
      return null

    def clazz = entity.class.name
    def id = entity.id

    def persisted = findByEntityClassAndEntityId(clazz, id)
    if(persisted)
      return persisted

    return new EntityWrapper(entityClass: clazz, entityId: id).save(flush: true, insert: true)
  }

  public T getEntity() {
    Class clazz = grailsApplication.getDomainClass(entityClass).clazz
    return clazz.get(entityId)
  }

  @Override
  Game extractGame() {
    def entity = getEntity()
    if (entity instanceof Game)
      return entity as Game

    entity.metaClass.respondsTo(entity, 'extractGame') ? entity.extractGame() : null
  }
}
