package cn.edu.ntu.javaee.springboot.quartz.duration.configuration;

import cn.edu.ntu.javaee.springboot.quartz.duration.properties.QDataSourceProperties;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zack <br>
 * @create 2020-12-24 00:38 <br>
 * @project springboot <br>
 */
@Configuration
@EnableConfigurationProperties(QDataSourceProperties.class)
public class QuartzConfiguration {

    @Resource QDataSourceProperties dataSourceProperties;

    @Bean
    @QuartzDataSource
    public DataSource qDataSource() {
        DruidDataSource source = new DruidDataSource();

        source.setUrl(dataSourceProperties.getUrl());
        source.setUsername(dataSourceProperties.getUsername());
        source.setPassword(dataSourceProperties.getPassword());
        source.setDriverClassName(dataSourceProperties.getDriverClassName());
        return source;
    }
}
