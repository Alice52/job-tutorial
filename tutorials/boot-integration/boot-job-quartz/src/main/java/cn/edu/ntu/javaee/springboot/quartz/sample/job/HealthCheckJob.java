package cn.edu.ntu.javaee.springboot.quartz.sample.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Slf4j
@Component
public class HealthCheckJob {

    @Resource Scheduler scheduler;

    @PostConstruct
    public void initJob() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("health-check-job", "default-group");
        JobKey jobKey = JobKey.jobKey("health-check-job", "default-group");

        JobDetail jobDetail =
                JobBuilder.newJob(HealthCheckJobBean.class).withIdentity(jobKey).build();

        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (ObjectUtil.isNull(trigger)) {
            trigger =
                    TriggerBuilder.newTrigger()
                            .withIdentity(triggerKey)
                            .withSchedule(
                                    SimpleScheduleBuilder.simpleSchedule()
                                            .withIntervalInSeconds(10)
                                            .repeatForever())
                            .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // already existence, so just re-start
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    class HealthCheckJobBean extends QuartzJobBean {
        @Override
        protected void executeInternal(JobExecutionContext jobExecutionContext)
                throws JobExecutionException {
            log.info("CHECK HEALTH IS OK IN {}", DateUtil.now());
        }
    }
}
