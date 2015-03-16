package ru.srms.larp.platform

import grails.transaction.Transactional

@Transactional
class GameAclService {

    def aclService
    def aclUtilService

    def createAcl(def object)
    {
        aclService.createAcl(
                aclUtilService.objectIdentityRetrievalStrategy.getObjectIdentity(object)
        )
    }
}
