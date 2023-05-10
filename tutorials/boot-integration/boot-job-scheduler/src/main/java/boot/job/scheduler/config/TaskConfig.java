package boot.job.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This config class is not necessary. And it can be replaced by yml config.
 *
 * @author zack <br>
 * @create 2021-04-26 16:43 <br>
 * @project boot-security-shiro <br>
 */
@Deprecated
@Configuration
@ComponentScan(basePackages = {"boot.job.scheduler.scheduler"})
public class TaskConfig implements SchedulingConfigurer {

    @Override
    @Deprecated
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    /** 这里等同于配置文件配置 */
    @Bean
    @Deprecated
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(30, Executors.defaultThreadFactory());
    }
}
