package cn.edu.ntu.javaee.springboot.quartz.sample.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author zack <br>
 * @create 2021-04-28<br>
 * @project integration <br>
 */
@Data
@Accessors(chain = true)
public class JobForm {
    /** 定时任务全类名 */
    @NotBlank(message = "类名不能为空")
    private String jobClassName;
    /** 任务组名 */
    @NotBlank(message = "任务组名不能为空")
    private String jobGroupName;
    /** 定时任务cron表达式 */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;
}
