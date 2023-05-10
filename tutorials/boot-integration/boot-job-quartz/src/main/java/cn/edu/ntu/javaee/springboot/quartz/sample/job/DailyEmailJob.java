package cn.edu.ntu.javaee.springboot.quartz.sample.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author zack <br>
 * @create 2021-04-29 12:02 <br>
 * @project boot-security-shiro <br>
 */
@Slf4j
@Component
public class DailyEmailJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("daily email send job is execute in: {}", DateUtil.now());
    }
}
