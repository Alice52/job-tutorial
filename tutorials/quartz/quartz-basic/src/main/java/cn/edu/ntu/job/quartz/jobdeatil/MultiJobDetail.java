package cn.edu.ntu.job.quartz.jobdeatil;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 1. One JobDetail can be triggered by more Trigger.
 *
 * <p>2. One JobDetail can just link to one Job, but one job can be referenced by many JobDetail.
 *
 * @author zack <br>
 * @create 2020/12/22 <br>
 * @project job <br>
 */
public class MultiJobDetail {
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // 1. get schedule
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 2.1 define job-detail
        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job", "group1").build();
        // 2.2 define job-detail
        JobDetail jobDetail2 =
                JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job2", "group1").build();

        // 3. define trigger1
        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("simple-trigger1", "auto")
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();

        Trigger trigger2 =
                TriggerBuilder.newTrigger()
                        .withIdentity("simple-trigger2", "auto")
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();

        // 4. build trigger and job-detail by schedule
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail2, trigger2);
        TimeUnit.SECONDS.sleep(50);
        // 5. shutdown
        scheduler.shutdown();
    }
}
