package cn.edu.ntu.job.quartz.properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zack <br>
 * @create 2020-12-23 19:22 <br>
 * @project job <br>
 */
public class JobProperties {
    private static final Logger LOG = LoggerFactory.getLogger(JobProperties.class);

    public static void main(String[] args) throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        LOG.info("pool size: {}", scheduler.getMetaData().getThreadPoolSize());
        LOG.info("scheduler name: {}", scheduler.getMetaData().getSchedulerName());
        scheduler.shutdown();
    }
}
