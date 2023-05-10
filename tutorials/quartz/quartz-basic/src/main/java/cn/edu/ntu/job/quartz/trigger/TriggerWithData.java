package cn.edu.ntu.job.quartz.trigger;

import cn.edu.ntu.job.quartz.job.SimpleJobForData;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author zack <br>
 * @create 2020-12-23 18:42 <br>
 * @project job <br>
 */
public class TriggerWithData {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerWithData.class);

    public static void main(String[] args) throws SchedulerException {

        // 1. get scheduler from factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 2. define job-details
        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJobForData.class)
                        .withIdentity("job-detail1", "group1")
                        // no means, it will be overwritten by setJobData
                        .usingJobData("age", 20)
                        .setJobData(
                                new JobDataMap(
                                        new HashMap<String, Object>(2) {
                                            {
                                                put("name", "zack1");
                                                put("age", 18);
                                            }
                                        }))
                        .build();

        // 3. define trigger
        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("triger1", "group1")
                        .usingJobData("name", "zack")
                        .usingJobData("company", "ntu")
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(1)
                                        .withRepeatCount(1))
                        .startNow()
                        .build();

        // 4. build job-detail with trigger by scheduler

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
