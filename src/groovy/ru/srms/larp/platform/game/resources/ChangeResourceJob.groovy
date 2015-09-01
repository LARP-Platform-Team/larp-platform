package ru.srms.larp.platform.game.resources

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

/**
 *
 * <p>Created 31.08.15</p>
 * @author kblokhin
 */
class ChangeResourceJob implements Job {
  @Override
  void execute(JobExecutionContext jobCtx) throws JobExecutionException {

    println "Job [${jobCtx.jobDetail.key.name}] is done! With ${jobCtx.trigger.jobDataMap.theProp}."
  }
}
