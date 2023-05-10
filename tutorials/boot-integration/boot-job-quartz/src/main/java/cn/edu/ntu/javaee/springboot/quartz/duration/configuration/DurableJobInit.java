package cn.edu.ntu.javaee.springboot.quartz.duration.configuration;

import cn.edu.ntu.javaee.springboot.quartz.quickstart.job.SimpleJob;
import com.google.common.collect.Sets;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * If QRTZ_* table is initialized and change initialize-schema to never, <br>
 * it will throw {@link org.quartz.ObjectAlreadyExistsException } due to system want init job to
 * database again.
 *
 * <p>So, if we donot want drop all quartz tables when each start-up, we can change
 * initialize-schema to never, but we should notice:<br>
 *
 * <pre>
 *    - we also cannot init job again due to it has exist in database.
 *    - so we can use controller api to init once,
 *    - or we can <code>scheduler.scheduleJob(jobDetail, Sets.newHashSet(trigger) , true);</code> to replace instance jb by using database job.
 * </pre>
 *
 * <p>notice:
 *
 * <pre>
 *    1. if the quartz is not running as cluster, I think it's ok to set initialize-schema=always.
 *    2. but if quartz run as cluster mode, it will is not allowed to delete table by some instance.
 *    3. and it is not recommended to use replace, which will replace quartz instance's job by database's job.
 *        it's is not harmony to update relation of trigger and job-detail, but it is no impact to change job logic:
 *        - such as: a-job is triggered by a-trigger store in database, but now I change a-trigger to trigger b-job, it will not expectation.
 *    4. so cluster mode, I recommend to use api to trigger relation of trigger and jon-detail.
 * </pre>
 *
 * @author zack <br>
 * @create 2020-12-24 19:30 <br>
 * @project springboot <br>
 */
// @Component
public class DurableJobInit {

    private static final Logger LOG = LoggerFactory.getLogger(DurableJobInit.class);
    @Resource Scheduler scheduler;

    @PostConstruct
    public void initJob() throws SchedulerException {
        final int poolSize = scheduler.getMetaData().getThreadPoolSize();
        LOG.info("quartz thread count in cluster job config: {}", poolSize);

        startJob("durable-job-1", "durable-trigger-1");
    }

    private void startJob(String jobName, String triggerName) throws SchedulerException {
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
        /** scheduler.scheduleJob(jobDetail, trigger); */
        scheduler.scheduleJob(jobDetail, Sets.newHashSet(trigger), true);
    }
}
