package cn.edu.ntu.javaee.springboot.quartz.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zack <br>
 * @create 2021-06-23 08:30 <br>
 * @project boot-security-shiro <br>
 */
@SpringBootApplication
@ComponentScan("cn.edu.ntu.javaee.springboot.quartz.quickstart")
public class QuartzQuickStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzQuickStartApplication.class, args);
    }
}
