package cn.edu.ntu.job.quartz.tutorials.state;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zack <br>
 * @create 2020-12-27<br>
 * @project quartz <br>
 */
public class JobStateUsage {

    private static final Logger LOG = LoggerFactory.getLogger(JobStateUsage.class);
    private static final Date startTime = nextGivenSecondDate(null, 10);

    @Test
    public void testJob1() throws SchedulerException, InterruptedException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // job1 will only run 5 times (at start time, plus 4 repeats), every 10 seconds
        JobDetail job1 = newJob(ColorJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger1 =
                newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4))
                        .build();

        // pass initialization parameters into the job
        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime1 = scheduler.scheduleJob(job1, trigger1);
        LOG.info(
                job1.getKey()
                        + " will run at: "
                        + scheduleTime1
                        + " and repeat: "
                        + trigger1.getRepeatCount()
                        + " times, every "
                        + trigger1.getRepeatInterval() / 1000
                        + " seconds");

        scheduler.start();

        Thread.sleep(60L * 1000L);
    }

    @Test
    public void testJob2() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // job2 will also run 5 times, every 10 seconds
        JobDetail job2 = newJob(ColorJob.class).withIdentity("job2", "group1").build();

        SimpleTrigger trigger2 =
                newTrigger()
                        .withIdentity("trigger2", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4))
                        .build();

        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime2 = scheduler.scheduleJob(job2, trigger2);
        LOG.info(
                job2.getKey().toString()
                        + " will run at: "
                        + scheduleTime2
                        + " and repeat: "
                        + trigger2.getRepeatCount()
                        + " times, every "
                        + trigger2.getRepeatInterval() / 1000
                        + " seconds");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        scheduler.start();
    }
}
