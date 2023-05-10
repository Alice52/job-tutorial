package cn.edu.ntu.javaee.springboot.quartz.quickstart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zack <br>
 * @create 2021-06-23 08:20 <br>
 * @project boot-security-shiro <br>
 */
@Configuration
@ConfigurationProperties(prefix = "boot.quartz.cron")
@Data
public class CronExpressionProperties {

    private String simpleJobCorn;
}
