package cn.edu.ntu.job.quartz.quickstart;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zack <br>
 * @create 2020/12/22 <br>
 * @project job <br>
 */
public class HelloQuartz {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1. get schedule
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 2. define job-detail
        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job", "group1").build();

        // 3. define trigger
        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("simple-trigger1", "auto")
                        .startNow()
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(2)
                                        .repeatForever())
                        .build();

        // 4. build trigger and job-detail by schedule
        scheduler.scheduleJob(jobDetail, trigger);

        TimeUnit.SECONDS.sleep(50);
        // 5. shutdown
        scheduler.shutdown();
    }
}
