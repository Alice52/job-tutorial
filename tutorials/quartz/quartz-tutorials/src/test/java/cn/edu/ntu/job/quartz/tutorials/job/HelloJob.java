package cn.edu.ntu.job.quartz.tutorials.job;

import cn.edu.ntu.job.quartz.tutorials.util.DFUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.StringJoiner;

/**
 * @author zack <br>
 * @create 2020-12-27 <br>
 * @project quartz <br>
 */
public class HelloJob implements Job {
    private static Logger LOG = LoggerFactory.getLogger(HelloJob.class);

    /**
     * Empty constructor for job initialization
     *
     * <p>Quartz requires a public empty constructor so that the scheduler can instantiate the class
     * whenever it needs.
     */
    public HelloJob() {}

    /**
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>
     * {@link org.quartz.Trigger}
     * </code> fires that is associated with the <code>Job</code>.
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        StringJoiner outStr =
                new StringJoiner(" ")
                        .add(this.getClass().getSimpleName())
                        .add(DFUtils.format(new Date()))
                        .add(Thread.currentThread().getName())
                        .add(context.getTrigger().getKey().getName())
                        .add(context.getJobDetail().getKey().getName());

        LOG.info(String.valueOf(outStr));
    }
}
