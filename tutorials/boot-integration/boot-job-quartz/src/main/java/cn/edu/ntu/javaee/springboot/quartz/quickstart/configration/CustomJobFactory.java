package cn.edu.ntu.javaee.springboot.quartz.quickstart.configration;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @author zack <br>
 * @create 2021-06-23 08:21 <br>
 * @project boot-security-shiro <br>
 */
public class CustomJobFactory extends SpringBeanJobFactory {
    @Autowired private AutowireCapableBeanFactory beanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {

        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);

        return jobInstance;
    }
}
