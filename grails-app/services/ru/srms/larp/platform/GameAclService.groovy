package ru.srms.larp.platform

import grails.transaction.Transactional

/**
 * TODO 1 - figure out more DRY way to add ACL on domain object creation
 * TODO 2 - figure out what is objectIdentityRetrievalStrategy and how it works
 */
@Transactional
class GameAclService {

    def aclService
    def aclUtilService

    def createAcl(def object)
    {
        aclService.createAcl(
                aclUtilService.objectIdentityRetrievalStrategy.getObjectIdentity(object))
    }
}
