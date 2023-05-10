package cn.edu.ntu.javaee.springboot.quartz.sample.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Slf4j
@Component
public class HelloJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) {
        log.warn("Hello Job 执行时间: {}", DateUtil.now());
    }
}
