package com.example.demo.core.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by cloud on 2019/03/11.
 */
@Component
@DisallowConcurrentExecution //不允许并发执行
public class SampleJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println(new Date().toLocaleString() + ":------>DoSampleJob");

    }
}
