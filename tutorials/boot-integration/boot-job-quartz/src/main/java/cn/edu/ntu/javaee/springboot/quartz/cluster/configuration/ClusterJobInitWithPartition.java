package cn.edu.ntu.javaee.springboot.quartz.cluster.configuration;

import cn.edu.ntu.javaee.springboot.quartz.quickstart.job.SimpleJob;
import com.google.common.collect.Sets;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * cluster lock is cluster wide: org.quartz.scheduler.instanceName<br>
 * application-cluster-partition-1.yml server will just run cluster-trigger-2 job.<br>
 * application-cluster-partition-2.yml server will run cluster-trigger-1/3 job.<br>
 * application-cluster-partition-3.yml server will run cluster-trigger-1/3 job.<br>
 *
 * <pre>
 *    1. so partition-1 and partition-2 is two quartz cluster with different name: org.quartz.scheduler.instanceName
 *    2. so partition-1 and  partition-2 will has lock in internal instance.
 *        - partition-1 cluster just init cluster-trigger-2
 *        - partition-2/3 cluster just init cluster-trigger-1/3 and 2/3 yml will has cluster lock to lb job.
 *    3. if partition-1 and partition-2 have same instanceName, it will be think one quartz cluster.
 *    4. it has 4 locks, 3 triggers,
 * </pre>
 *
 * @author zack <br>
 * @create 2020-12-24 19:30 <br>
 * @project springboot <br>
 */
@Component
public class ClusterJobInitWithPartition {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterJobInitWithPartition.class);
    @Resource Scheduler scheduler;

    @Value("${spring.quartz.properties.org.quartz.scheduler.instanceName}")
    private String instanceName;

    @PostConstruct
    public void initJob() throws SchedulerException {
        final int poolSize = scheduler.getMetaData().getThreadPoolSize();
        LOG.info("quartz thread count in cluster job config: {}", poolSize);

        startJob("cluster-job-1", "cluster-trigger-1", "order-service-job");
        startJob("cluster-job-2", "cluster-trigger-2", "cart-service-job");
        startJob("cluster-job-3", "cluster-trigger-3", "order-service-job");
    }

    /**
     * Recommend to use api to trigger relation between job-detail and triggers.
     *
     * @param jobName
     * @param triggerName
     * @throws SchedulerException
     */
    @Deprecated
    private void startJob(String jobName, String triggerName, String partition)
            throws SchedulerException {

        if (!partition.equals(instanceName)) {
            return;
        }

        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJob.class).withIdentity(jobName).storeDurably(true).build();

        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity(triggerName)
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(5)
                                        .repeatForever())
                        .startNow()
                        .build();
        scheduler.scheduleJob(jobDetail, Sets.newHashSet(trigger), true);
    }
}
