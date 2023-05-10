package boot.job.xxl.job.config;

import boot.job.xxl.job.config.props.XxlJobProps;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zack <br>
 * @create 2021-05-04 19:15 <br>
 * @project integration <br>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProps.class)
public class XxlJobConfig {

    @Resource private XxlJobProps xxlJobProps;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {

        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProps.getAdmin().getAddress());
        xxlJobSpringExecutor.setAccessToken(xxlJobProps.getAccessToken());
        xxlJobSpringExecutor.setAppname(xxlJobProps.getExecutor().getAppName());
        xxlJobSpringExecutor.setIp(xxlJobProps.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProps.getExecutor().getPort());
        xxlJobSpringExecutor.setLogPath(xxlJobProps.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProps.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     * <p>1、引入依赖： <dependency> <groupId>org.springframework.cloud</groupId>
     * <artifactId>spring-cloud-commons</artifactId> <version>${version}</version> </dependency>
     *
     * <p>2、配置文件，或者容器启动变量 spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     * <p>3、获取IP String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */
}
