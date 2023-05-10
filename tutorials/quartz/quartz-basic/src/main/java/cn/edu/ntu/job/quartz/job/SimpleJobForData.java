package cn.edu.ntu.job.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zack <br>
 * @create 2020-12-23 18:41 <br>
 * @project job <br>
 */
public class SimpleJobForData implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleJobForData.class);

    private int age;
    private String company;

    /**
     * This value will be injected by quartz.
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();
        LOG.info("trigger data map: {}", triggerDataMap.get("name"));
        LOG.info("job data map: {}", context.getJobDetail().getJobDataMap().get("name"));
        LOG.info("merged data map: {}", context.getMergedJobDataMap().get("name"));

        LOG.info("age value: {}", age);
        LOG.info("age value: {}", company);
    }
}
