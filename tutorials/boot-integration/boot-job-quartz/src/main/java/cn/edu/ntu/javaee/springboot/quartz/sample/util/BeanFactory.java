package cn.edu.ntu.javaee.springboot.quartz.sample.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Slf4j
public enum BeanFactory {
    INSTANCE;

    public <T> T getBean(Class<T> checkType, String className) {

        try {
            Class<T> clz = (Class<T>) Class.forName(className);
            Object obj = clz.newInstance();
            if (!checkType.isInstance(obj)) {
                throw new Exception("对象跟字节码不兼容");
            }
            return (T) obj;
        } catch (Exception e) {
            log.info("{}", e);
        }

        return null;
    }
}
