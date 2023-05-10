package cn.edu.ntu.job.quartz.job;

import cn.edu.ntu.job.quartz.utils.UTCTimeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * SimpleJob: output to console
 *
 * @author zack
 * @create 2019-09-21 20:15
 * @function it is a simple job to output in console.
 */
public class SimpleJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleJob.class);
    private static final DateTimeFormatter ofPattern =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        StringJoiner outStr =
                new StringJoiner(" ")
                        .add(this.getClass().getSimpleName())
                        .add(UTCTimeUtil.localToUtc(LocalDateTime.now()).format(ofPattern))
                        .add(Thread.currentThread().getName())
                        .add(jobExecutionContext.getTrigger().getKey().getName());

        LOG.info("execute quartz job: {}", outStr);
    }
}
