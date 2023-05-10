package cn.edu.ntu.javaee.springboot.quartz.sample.mapper;

import cn.edu.ntu.javaee.springboot.quartz.sample.model.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Mapper
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
