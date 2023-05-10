package cn.edu.ntu.job.quartz.tutorials.util;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author zack <br>
 * @create 2020-12-27<br>
 * @project quartz <br>
 */
public class JobLogUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JobLogUtils.class);

    public static void logSimpleJob(JobDetail job, SimpleTrigger trigger, Date ft) {
        LOG.info(
                job.getKey()
                        + " will run at: "
                        + ft
                        + " and repeat: "
                        + trigger.getRepeatCount()
                        + " times, every "
                        + trigger.getRepeatInterval() / 1000
                        + " seconds");
    }

    public static void logCronJob(JobDetail job, CronTrigger trigger, Date ft) {
        LOG.info(
                job.getKey()
                        + " has been scheduled to run at: "
                        + ft
                        + " and repeat based on expression: "
                        + trigger.getCronExpression());
    }
}
