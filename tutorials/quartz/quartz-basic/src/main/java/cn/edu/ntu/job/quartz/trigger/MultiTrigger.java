package cn.edu.ntu.job.quartz.trigger;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 1. The trigger must have unique withIdentity, it's to say, cannot has same name and group-name at
 * same time.
 *
 * <p>2. Many Triggers can point to the same Job detail, but a single Trigger can only point to one
 * Job detail.
 *
 * <p>If the JobDetail is triggered by <code> scheduler.scheduleJob(jobDetail, trigger);</code>, so
 * another trigger cannot trigger in this way. <br>
 * And must use <code> scheduler.scheduleJob(trigger);</code> method. *
 *
 * @author zack <br>
 * @create 2020/12/22 <br>
 * @project job <br>
 */
public class MultiTrigger {
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // 1. get schedule
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 2. define job-detail
        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job", "group1").build();

        // 3.1 define trigger1
        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("simple-trigger1", "auto")
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();

        // 3.2 define trigger3
        Trigger trigger2 =
                TriggerBuilder.newTrigger()
                        .withIdentity("simple-trigger2", "manual")
                        .forJob(jobDetail)
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();

        // 4. build trigger and job-detail by schedule
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(trigger2);
        /**
         * This will throw exception: {@link org.quartz.ObjectAlreadyExistsException } <br>
         * Unable to store Job : 'group1.simple-job', because one already exists with this
         * identification.
         */
        /** scheduler.scheduleJob(jobDetail, trigger2); */
        TimeUnit.SECONDS.sleep(50);
        // 5. shutdown
        scheduler.shutdown();
    }
}
