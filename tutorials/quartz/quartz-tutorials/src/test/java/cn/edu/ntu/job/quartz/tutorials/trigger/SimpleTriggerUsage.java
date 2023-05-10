package cn.edu.ntu.job.quartz.tutorials.trigger;

import cn.edu.ntu.job.quartz.tutorials.job.HelloJob;
import cn.edu.ntu.job.quartz.tutorials.util.JobLogUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * {@link DateBuilder#nextGivenSecondDate }: returns a date that is rounded to the next even
 * multiple of the given minute.
 *
 * @author zack <br>
 * @create 2020-12-27 <br>
 * @project quartz <br>
 */
public class SimpleTriggerUsage {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleTriggerUsage.class);
    // get a "nice round" time a few seconds in the future...
    private static final Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
    private static Scheduler scheduler = null;

    @Before
    public void initScheduler() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    @After
    public void beforeTerminal() throws SchedulerException, InterruptedException {
        scheduler.start();
        // wait 65 seconds to show job
        TimeUnit.SECONDS.sleep(6500);
        scheduler.shutdown(true);
        LOG.info("Shutdown Complete");
    }

    @Test
    public void testSimpleUsage() throws SchedulerException {

        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger =
                (SimpleTrigger)
                        newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();
        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);
    }

    /**
     * One JobDetail is triggered by more triggers: notice second trigger method.
     *
     * @throws SchedulerException
     */
    @Test
    public void testRepeatAndOneJobWithMoreTrigger() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job3", "group1").build();

        SimpleTrigger trigger =
                newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startAt(startTime)
                        .withSchedule(
                                simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);

        SimpleTrigger trigger2 =
                newTrigger()
                        .withIdentity("trigger2", "group2")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2))
                        .forJob(job)
                        .build();
        ft = scheduler.scheduleJob(trigger2);
        JobLogUtils.logSimpleJob(job, trigger2, ft);
    }

    /**
     * run 6 times[run once and repeat 5 more times], <br>
     * and repeat every 10 seconds.
     */
    @Test
    public void testCount() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job4", "group1").build();

        SimpleTrigger trigger =
                newTrigger()
                        .withIdentity("trigger4", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);
    }

    /** job5 will run once, five minutes in the future */
    @Test
    public void testFutureApi() throws SchedulerException {

        JobDetail job = newJob(HelloJob.class).withIdentity("job5", "group1").build();

        SimpleTrigger trigger =
                (SimpleTrigger)
                        newTrigger()
                                .withIdentity("trigger5", "group1")
                                .startAt(futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);
    }

    /** job6 will run indefinitely, every 40 seconds */
    @Test
    public void testRunForever() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job6", "group1").build();

        SimpleTrigger trigger =
                newTrigger()
                        .withIdentity("trigger6", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever())
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);
    }

    /**
     * scheduled after start() has been called, it's to say, <br>
     * if I call scheduler.start() before, now I just need add job to scheduler is OK.
     *
     * @throws SchedulerException
     */
    @Test
    public void testExecuteAfterStartThenRescheduling() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job7", "group1").build();

        SimpleTrigger trigger =
                newTrigger()
                        .withIdentity("trigger7", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20))
                        .build();
        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logSimpleJob(job, trigger, ft);

        SimpleTrigger trigger2 =
                newTrigger()
                        .withIdentity("trigger7", "group1")
                        .startAt(startTime)
                        .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(20))
                        .build();

        Date ft2 = scheduler.rescheduleJob(trigger2.getKey(), trigger2);
        LOG.info("job7 rescheduled to run at: " + ft2);
    }

    /**
     * jobs can be fired directly, rather than waiting for a trigger, <br>
     * and it can just run once.
     *
     * @throws SchedulerException
     */
    @Test
    public void testManualTriggerJonWithoutTrigger() throws SchedulerException {
        JobDetail job =
                newJob(HelloJob.class).withIdentity("job8", "group1").storeDurably().build();

        scheduler.addJob(job, true);
        LOG.info("'Manually' triggering job8...");
        scheduler.triggerJob(jobKey("job8", "group1"));
    }

    @Test
    public void testDisplayMetaData() throws SchedulerException {
        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = scheduler.getMetaData();
        LOG.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }
}
