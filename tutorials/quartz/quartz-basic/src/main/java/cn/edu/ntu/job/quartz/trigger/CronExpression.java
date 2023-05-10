package cn.edu.ntu.job.quartz.trigger;

import cn.edu.ntu.job.quartz.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zack <br>
 * @create 2020/12/22 <br>
 * @project job <br>
 */
public class CronExpression {

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 2. define job-detail
        JobDetail jobDetail =
                JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job", "group1").build();

        // 3.1 define trigger
        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity("cron-test", "auto")
                        .startNow()
                        /**
                         *
                         *
                         * <pre>
                         *
                         * // 1. Execute from 1s, and each 5s will execute.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("1/5 * * * * ? *"))
                         * // 2. Execute each 0s, 40s
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("0/40 * * * * ? *"))
                         * // 3. Execute each 45s
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("45/40 * * * * ? *"))
                         * // 4. Execute in last day of per month
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * L * ? *"))
                         * // 5. Execute in (last day - 3d) per month
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * L-3 * ? *"))
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * 1-3,L-3 * ? *")) // invalid
                         * // 6. Execute in third SAT of each week
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * ? * 3L *"))
                         * // 7. Execute in first weekday per month, even though 1th is SAT, which nearest weekday is last month's Fri.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * 1W * ？ *"))
                         * // 8. The 'W' character can only be specified when the day-of-month is a single day, not a range or list of days.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * 1-4W * ？ *")) // invalid
                         * // 9. Execute in the last weekday of month
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * LW * ？ *"))
                         * // 10. Execute in third Fri per month
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * ？ * 6#3 *"))
                         * // 11. Execute in 8th Fri day per month, it's means never execute
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * ？ * 6#8 *"))
                         * // 12. invalid, it must single value when used.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("* * * ？ * 6#8,6#3 *"))
                         * // 13. Execute every 10-60, 0-5 seconds every minute， 56 times.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("10-5 * * * * ? *"))
                         * // 14. Execute every 5-10 seconds every minute， 6 times.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("5-10 * * * * ? *"))
                         * // 14. Execute every 5-10 seconds every minute， 6 times.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("5-10 * * * * ? *"))
                         * // 16. Execute every 5, 10, 11 seconds every minute， 3 times.
                         * // .withSchedule(CronScheduleBuilder.cronSchedule("5,10,11 * * * * ? * -"))
                         * </pre>
                         */
                        .withSchedule(CronScheduleBuilder.cronSchedule("* * * ? * ? *"))
                        .build();

        // 4. bind
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
