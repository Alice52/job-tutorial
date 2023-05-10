package cn.edu.ntu.javaee.springboot.quartz.sample.util;

import cn.edu.ntu.javaee.springboot.quartz.sample.job.BaseJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.StringJoiner;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Slf4j
public class JobUtil {
    /**
     * 根据全类名获取Job实例
     *
     * @param className Job全类名
     * @return {@link BaseJob} 实例
     * @throws Exception 泛型获取异常
     */
    @SneakyThrows
    public static BaseJob getClass(String className) {

        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            StringJoiner joiner = new StringJoiner(".");
            joiner.add(BaseJob.class.getPackage().getName()).add(className);
            clazz = Class.forName(joiner.toString());
        }

        return (BaseJob) clazz.newInstance();
    }

    @SneakyThrows
    @Deprecated
    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner(".");
        joiner.add(BaseJob.class.getPackage().getName()).add("HelloJob");
        Class<?> clazz = Class.forName(joiner.toString());
        BaseJob job = (BaseJob) clazz.newInstance();
        log.info("{}", job);
    }
}
