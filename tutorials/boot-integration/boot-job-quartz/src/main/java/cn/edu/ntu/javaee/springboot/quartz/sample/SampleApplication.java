package cn.edu.ntu.javaee.springboot.quartz.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * - 多实例会自动出现LB的效果
 *
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
