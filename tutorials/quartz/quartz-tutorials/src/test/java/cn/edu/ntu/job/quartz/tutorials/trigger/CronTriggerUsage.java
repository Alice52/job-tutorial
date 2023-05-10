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

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * https://github.com/Alice52/project/issues/119#issuecomment-749516486
 *
 * @author zack <br>
 * @create 2020-12-27<br>
 * @project quartz <br>
 */
public class CronTriggerUsage {

    private static final Logger LOG = LoggerFactory.getLogger(CronTriggerUsage.class);
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
        TimeUnit.MINUTES.sleep(6500);
        scheduler.shutdown(true);
        LOG.info("Shutdown Complete");
    }

    /**
     * job 1 will run every 20 seconds
     *
     * @throws SchedulerException
     */
    @Test
    public void testJob1() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger1", "group1")
                        .withSchedule(cronSchedule("0/20 * * * * ?"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /**
     * job 2 will run every other minute (at 15 seconds past the minute)
     *
     * @throws SchedulerException
     */
    @Test
    public void testJob2() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job2", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger2", "group1")
                        .withSchedule(cronSchedule("15 0/2 * * * ?"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /** job 3 will run every other minute but only between 8am and 5pm */
    @Test
    public void testJob3() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job3", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger3", "group1")
                        .withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /** job 4 will run every three minutes but only between 5pm and 11pm */
    @Test
    public void testJob4() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job4", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger4", "group1")
                        .withSchedule(cronSchedule("0 0/3 17-23 * * ?"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /** job 5 will run at 10am on the 1st and 15th days of the month */
    @Test
    public void testJob5() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job5", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger5", "group1")
                        .withSchedule(cronSchedule("0 0 10am 1,15 * ?"))
                        .build();
        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /**
     * job 6 will run every 30 seconds but only on Weekdays (Monday through Friday)
     *
     * @throws SchedulerException
     */
    @Test
    public void testJob6() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job6", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger6", "group1")
                        .withSchedule(cronSchedule("0,30 * * ? * MON-FRI"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }

    /** job 7 will run every 30 seconds but only on Weekends (Saturday and Sunday) */
    @Test
    public void testJob7() throws SchedulerException {
        JobDetail job = newJob(HelloJob.class).withIdentity("job7", "group1").build();

        CronTrigger trigger =
                newTrigger()
                        .withIdentity("trigger7", "group1")
                        .withSchedule(cronSchedule("0,30 * * ? * SAT,SUN"))
                        .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        JobLogUtils.logCronJob(job, trigger, ft);
    }
}
