package cn.edu.ntu.job.quartz.tutorials.scheduler;

import cn.edu.ntu.job.quartz.tutorials.job.HelloJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * evenMinuteDate: return next round minute.
 *
 * @author zack <br>
 * @create 2020-12-27 <br>
 * @project quartz <br>
 */
public class SimpleScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleScheduler.class);

    @Test
    public void testScheduler() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        // computer a time that is on the next round minute
        Date runTime = evenMinuteDate(new Date());
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();
        // Trigger the job to run on the next round minute
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        LOG.info(job.getKey() + " will run at: " + runTime);

        scheduler.start();
        // wait 65 seconds to show job
        TimeUnit.SECONDS.sleep(65);

        scheduler.shutdown(true);
        LOG.info("Shutdown Complete");
    }
}
