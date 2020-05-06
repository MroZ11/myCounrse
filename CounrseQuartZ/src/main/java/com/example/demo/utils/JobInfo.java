package com.example.demo.utils;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cloud on 2020/4/30.
 */
@Getter
@Setter
public class JobInfo implements Serializable {
    private String jobName;//任务名

    private String jobGroup;//任务组

    private String jobClass;//任务执行类

    private String jobDescription;//任务描述

    private String triggerName;//触发器名

    private String triggerGroup;//触发器组

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date triggerStartTime;//触发器开始时间

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date triggerEndTime;//触发器终止时间

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date triggerPreviousFireTime;//触发器上次触发时间

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date triggerNextFireTime;//触发器下次触发时间

    private String triggerDescription;//触发器描述

    private String triggerCronExpression;//触发器cron表达式 对cronTrigger才有

    private String triggerStatus;//触发器状态

    private String calendarName;//日历名
    private String calendarDesc;//日历描述
}
