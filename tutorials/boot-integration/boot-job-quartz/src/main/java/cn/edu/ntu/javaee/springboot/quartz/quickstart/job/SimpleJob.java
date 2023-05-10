package cn.edu.ntu.javaee.springboot.quartz.quickstart.job;

import cn.edu.ntu.javaee.springboot.quartz.utils.UTCTimeUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * @author zack <br>
 * @create 2020-12-23 21:14 <br>
 * @project springboot <br>
 */
@Slf4j
public class SimpleJob extends QuartzJobBean {

    private static final DateTimeFormatter ofPattern =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String name;

    /** Invoked if a Job data map entry with that name */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        val outStr =
                new StringJoiner(" ")
                        .add(this.getClass().getSimpleName())
                        .add(UTCTimeUtil.localToUtc(LocalDateTime.now()).format(ofPattern))
                        .add(Thread.currentThread().getName())
                        .add(jobExecutionContext.getTrigger().getKey().getName());

        log.info("execute quartz job: {}", outStr);
    }
}
