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
    def rule = PeriodicRule.get(id)
    if(!rule) {
      logger.error("There is no rule with id ${id}. In trigger ${jobCtx.trigger.key.name}.")
      return
    }
    ResourceService resourceService = resourceService()
    if(resourceService == null) {
      logger.error("ResourceService is not initialized. In trigger ${jobCtx.trigger.key.name}.")
      return
    }
/*
// save a log entry
    TransferLogEntry logEntry = new TransferLogEntry(
        value: data.transferValue, comment: data.comment, source: source, target: target,
        sourceName: source.fullId, targetName: target.fullId)
    if (logEntry.validate())
      logEntry.save(flush: true)
 */


    println "Job [${jobCtx.jobDetail.key.name}] is done! With ${jobCtx.trigger.jobDataMap.ruleId}."
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
