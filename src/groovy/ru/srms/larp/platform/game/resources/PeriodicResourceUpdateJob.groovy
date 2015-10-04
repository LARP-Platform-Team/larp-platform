package ru.srms.larp.platform.game.resources

import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobKey
import ru.srms.larp.platform.ResourceService

/**
 * TODO try using grails-app/jobs
 * <p>Created 31.08.15</p>
 */
class PeriodicResourceUpdateJob implements Job {

  public static final String PERIODIC_UPDATE_JOB_KEY = "PeriodicResourceUpdate"
  private static final Logger logger = Logger.getLogger(PeriodicResourceUpdateJob.class)

  @Override
  void execute(JobExecutionContext jobCtx) throws JobExecutionException {
    if(!jobCtx.trigger.jobDataMap.ruleId) {
      logger.error("Trigger ${jobCtx.trigger.key.name} has no ruleId data.")
      return
    }

    def id = jobCtx.trigger.jobDataMap.ruleId as Long
    def rule = ResourcePeriodicRule.get(id)
    if(!rule) {
      logger.error("There is no rule with id ${id}. In trigger ${jobCtx.trigger.key.name}.")
      return
    }
    ResourceService resourceService = resourceService()
    if(resourceService == null) {
      logger.error("ResourceService is not initialized. In trigger ${jobCtx.trigger.key.name}.")
      return
    }

    try {
      if(rule.extractGame().active)
        resourceService.applyPeriodicRule(rule)
    } catch (Exception e) {
      logger.error("Applying of rule [${rule.id}] failed.", e)
    }
  }

  private def resourceService() {
    def ctx = ServletContextHolder.servletContext.getAttribute(
        GrailsApplicationAttributes.APPLICATION_CONTEXT)
    ctx.resourceService
  }

  public static def getJobKey() {
    JobKey.jobKey(PeriodicResourceUpdateJob.PERIODIC_UPDATE_JOB_KEY, "LarpPlatform")
  }
}
