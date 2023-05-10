package boot.job.xxl.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://github.com/xkcoding/spring-boot-demo/blob/master/demo-task-xxl-job/README.md<br>
 *
 * <pre>
 *    1. config admin: registry.cn-shanghai.aliyuncs.com/alice52/xxl-job-admin:2.3.1
 *    2. this module is executor: do add executor to admin as executor
 *    3. create job in this executor.
 *    4. need modify:
 *      - must add executor manually? why not auto detect
 *      - create task manually
 *      - fill job-handler manually when create task? why not drop-down list
 * </pre>
 *
 * @author zack <br>
 * @create 2021-04-26 10:04 <br>
 * @project boot-job-quartz <br>
 */
@SpringBootApplication
public class XxlJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobApplication.class, args);
    }
}
