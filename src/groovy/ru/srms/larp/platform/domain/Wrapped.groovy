package ru.srms.larp.platform.domain

import ru.srms.larp.platform.EntityWrapper

trait Wrapped<T> {
  public EntityWrapper<T> getWrapper() {
    EntityWrapper.findByEntityClassAndEntityId(this.class.name, this.id)
  }

  public def deleteWrapper() {
    getWrapper()?.delete(flush: true)
  }
}