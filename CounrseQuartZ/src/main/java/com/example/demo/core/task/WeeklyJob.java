package com.example.demo.core.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by cloud on 2020/4/30.
 */
@Component
@DisallowConcurrentExecution //不允许并发执行
public class WeeklyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date().toLocaleString() + ":------>This is a WeeklyJob ");
    }
}
