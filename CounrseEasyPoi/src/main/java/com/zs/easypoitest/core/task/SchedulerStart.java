package com.zs.easypoitest.core.task;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by cloud on 2019/03/11.
 */
@Component
public class SchedulerStart implements CommandLineRunner {

    @Resource
    Scheduler scheduler;


    @Override
    public void run(String... strings) throws Exception {
        //开启job
        JobDetail job = newJob(SampleJob.class).withIdentity("myjob").build();

        //可以用addCalendar排除日期或规定个执行日期
        HolidayCalendar holidays = new HolidayCalendar();
        Calendar calendar = new GregorianCalendar(2020, 3, 30);
        holidays.addExcludedDate(calendar.getTime());
        scheduler.addCalendar("holidays", holidays, false, false);
        //WeeklyCalendar 可以用来排除每周星期几，默认WeeklyCalendar会排除星期六星期天

        //配置计划任务的定时器(触发器的作用应给job触发条件,同一个job可以添加多触发条件，不同的job也可以被相同的触发器触发)
        Trigger trigger = newTrigger().withIdentity("myjob").withSchedule(cronSchedule("*/30 * * * * ?")).modifiedByCalendar("holidays").build();

        scheduler.scheduleJob(job, trigger);//获得首次将要执行计划任务的时间，待会儿println出来
        scheduler.start();
    }

    public static void main(String[] args) {
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        weeklyCalendar.setDayExcluded(Calendar.SUNDAY,false);
        System.out.println(Arrays.toString(weeklyCalendar.getDaysExcluded()));



    }








}
