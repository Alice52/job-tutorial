package cn.edu.ntu.javaee.springboot.quartz.quickstart.configration;

import cn.edu.ntu.javaee.springboot.quartz.quickstart.job.SimpleJob;
import cn.edu.ntu.javaee.springboot.quartz.quickstart.properties.CronExpressionProperties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Un-Recommend.
 *
 * <p>Beans of the following types are automatically picked up and associated with the Scheduler.
 *
 * @author zack
 * @create 2019-09-22 0:58
 * @function
 */
@Slf4j
@Configuration
public class SchedulerConfig {

    @Autowired private DataSource dataSource;

    @Autowired private JobFactory jobFactory;

    @Autowired private CronExpressionProperties cronExpressionProperties;

    @Bean
    public Scheduler scheduler() throws Exception {
        log.info("SchedulerConfigAnton");
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.start();
        return scheduler;
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        CustomJobFactory jobFactory = new CustomJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setSchedulerName("Cluster_Scheduler");
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setOverwriteExistingJobs(true);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setTaskExecutor(schedulerThreadPool());
        Trigger[] triggers =
                new Trigger[] {
                    triggerSimplePrintWords().getObject(),
                };

        // remove corn is past
        List<Trigger> collect =
                Arrays.stream(triggers)
                        .filter(
                                x ->
                                        x.getFireTimeAfter(new Date()) != null
                                                || !(x instanceof CronTrigger))
                        .collect(Collectors.toList());
        factory.setTriggers(collect.toArray(new Trigger[1]));

        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /*
        循环执行
    */
    @Bean
    public JobDetailFactoryBean jobSamplePrintWords() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        jobDetailFactoryBean.setName("SimpleJob");
        jobDetailFactoryBean.setJobClass(SimpleJob.class);
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean triggerSimplePrintWords() {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        simpleTriggerFactoryBean.setJobDetail(jobSamplePrintWords().getObject());

        Date startTime = new Date();
        startTime.setTime(startTime.getTime() + 3000);
        simpleTriggerFactoryBean.setName("SimpleJob");
        simpleTriggerFactoryBean.setStartTime(startTime);
        // 不设置代表一直重复执行
        // simpleTriggerFactoryBean.setRepeatCount(2000);
        simpleTriggerFactoryBean.setRepeatInterval(600000);

        return simpleTriggerFactoryBean;
    }

    @Bean
    public Executor schedulerThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);

        return executor;
    }
}
