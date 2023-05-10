package cn.edu.ntu.javaee.springboot.quartz.cluster.configuration;

import cn.edu.ntu.javaee.springboot.quartz.quickstart.job.SimpleJob;
import com.google.common.collect.Sets;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * When quartz run as cluster mode, the following conditions must be met:
 *
 * <pre>
 *     1. must be based on jdbc store
 *     2. first start-up with <code>initialize-schema: always</code>
 *     3. change config to <code> initialize-schema: never </code>
 *     4. all cluster instance cannot be allowed to delete quartz tables
 *     5. cluster quartz query database with lock, so it maybe become performance bottlenecks
 *          - cluster-partition
 *
 *     6. if this job run by different clusterName, it will also 2 cluster and each cluster has all job triggers.
 *          - it will execute separate: each cluster will execute job fully[One is work fined]
 *          - and it will have 6 triggers total， 4 locks
 *
 *     7. 如果 instanceName 不同,则代表不同的集群, 这样的集群每个都可以完成 job 的全部任务, 因此, 使用不同集群时, 需要配置每个集群处理指定的 Job, 不需要每个集群都处理全部的 Job[没有意思, 加大服务器压力]
 * </pre>
 *
 * @author zack <br>
 * @create 2020-12-24 19:30 <br>
 * @project springboot <br>
 */
// @Component
public class ClusterJobInit {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterJobInit.class);
    @Resource Scheduler scheduler;

    /**
     * Recommend to use api to trigger relation between job-detail and triggers.
     *
     * @throws SchedulerException
     */
    @PostConstruct
    public void initJob() throws SchedulerException {
        final int poolSize = scheduler.getMetaData().getThreadPoolSize();
        LOG.info("quartz thread count in cluster job config: {}", poolSize);

        JobDetail jobDetail1 =
                JobBuilder.newJob(SimpleJob.class)
                        .withIdentity("cluster-job-1")
                        .storeDurably(true)
                        .build();
        Trigger trigger1 =
                TriggerBuilder.newTrigger()
                        .withIdentity("cluster-trigger-1")
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(5)
                                        .repeatForever())
                        .startNow()
                        .build();

        JobDetail jobDetail2 =
                JobBuilder.newJob(SimpleJob.class)
                        .withIdentity("cluster-job-2")
                        .storeDurably(true)
                        .build();
        Trigger trigger2 =
                TriggerBuilder.newTrigger()
                        .withIdentity("cluster-trigger-2")
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(5)
                                        .repeatForever())
                        .startNow()
                        .build();

        JobDetail jobDetail3 =
                JobBuilder.newJob(SimpleJob.class)
                        .withIdentity("cluster-job-3")
                        .storeDurably(true)
                        .build();
        Trigger trigger3 =
                TriggerBuilder.newTrigger()
                        .withIdentity("cluster-trigger-3")
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(5)
                                        .repeatForever())
                        .startNow()
                        .build();

        scheduler.scheduleJob(jobDetail1, Sets.newHashSet(trigger1), true);
        scheduler.scheduleJob(jobDetail2, Sets.newHashSet(trigger2), true);
        scheduler.scheduleJob(jobDetail3, Sets.newHashSet(trigger3), true);
    }
}
