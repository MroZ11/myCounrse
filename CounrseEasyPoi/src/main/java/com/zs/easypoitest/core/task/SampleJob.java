package com.zs.easypoitest.core.task;

import com.zs.easypoitest.dao.CarDao;
import com.zs.easypoitest.model.Car;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by cloud on 2019/03/11.
 */
@Component
@DisallowConcurrentExecution //不允许并发执行
public class SampleJob extends QuartzJobBean {

    @Autowired
    CarDao carDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Car> cars = carDao.findAll();
        System.out.println(new Date().toLocaleString()+":------>DoSampleJob");

    }
}
