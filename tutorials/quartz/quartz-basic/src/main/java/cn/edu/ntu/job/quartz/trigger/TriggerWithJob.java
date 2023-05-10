package cn.edu.ntu.job.quartz.trigger;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * If {@link Trigger } is linked for job by <code>.forJob(str, str)</code>, it can trigger by
 * scheduler without specify job arg: <br>
 * 1. job must be must <code>.storeDurably(true)</code> <br>
 * 2. job must be added to scheduler before. <br>
 * 3. {@link Trigger } must call <code>.forJob(str, str)</code> method. <br>
 *
 * <p>If the JobDetail is triggered, it can call <code> scheduler.scheduleJob(newTrigger)</code>
 * directly.
 *
 * @author zack <br>
 * @create 2020-12-23 19:45 <br>
 * @project job <br>
 */
public class TriggerWithJob {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerWithJob.class);

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail newJobDetail =
                JobBuilder.newJob(SimpleJob.class)
                        .withIdentity("new-job", "group1")
                        .storeDurably(true)
                        .build();
        Trigger newTrigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("new-trigger", "auto")
                        .forJob("new-job", "group1")
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();
        // error: due to scheduler cannot know newJobDetail
        // scheduler.scheduleJob(newTrigger);

        // it's ok.
        scheduler.addJob(newJobDetail, true);
        scheduler.scheduleJob(newTrigger);
    }
}
