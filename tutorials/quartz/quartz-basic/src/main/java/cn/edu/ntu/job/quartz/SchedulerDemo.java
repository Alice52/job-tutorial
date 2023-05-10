package cn.edu.ntu.job.quartz;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zack
 * @create 2019-09-21 23:01
 * @function
 */
public class SchedulerDemo {

    private static final String DEFAULT_CRON = "0 0/59 * * * ?";
    private static String CORN_ADD_USER = DEFAULT_CRON;

    public static void main(String[] args) throws SchedulerException {
        // 1.Scheduler : Get from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2.JobDetail : Use the JobBuilder
        JobDetail simpleJobDetail =
                JobBuilder.newJob(SimpleJob.class) // load the job class and bind the HelloJob
                        .withIdentity(
                                "simple-job1",
                                "simple-group1") // arg1: task(job) name  arg2: group name
                        .build();
        // 2.2 JobDetail : Use the JobBuilder
        JobDetail cronJobDetail =
                JobBuilder.newJob(SimpleJob.class) // load the job class and bind the HelloJob
                        .withIdentity(
                                "cron-job1",
                                "cron-group1") // arg1: task(job) name  arg2: group name
                        .build();

        // 3. SimpleTrigger
        SimpleTrigger simpleTrigger =
                TriggerBuilder.newTrigger()
                        .startNow()
                        // .withIdentity( "simple-trigger1", "simple-group1") // arg1: trigger name
                        // arg2: group
                        // name [not same with job group]
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .repeatSecondlyForever(5)) // 5 second exec
                        .build();

        // 3.2 cronTrigger
        CronTrigger cronTrigger =
                TriggerBuilder.newTrigger()
                        .startNow()
                        .withIdentity(
                                "cron-trigger1",
                                "cron-group1") // arg1: trigger name  arg2: group name [not same
                        // with job group]
                        .withSchedule(CronScheduleBuilder.cronSchedule(CORN_ADD_USER))
                        .build();

        // 4. bind the jobDetail and trigger
        scheduler.scheduleJob(simpleJobDetail, simpleTrigger);
        scheduler.scheduleJob(cronJobDetail, cronTrigger);
        // scheduler.scheduleJob(simpleJobDetail, cronTrigger);  // cannot log error

        // 5. start the scheduler
        scheduler.start();
    }
}
