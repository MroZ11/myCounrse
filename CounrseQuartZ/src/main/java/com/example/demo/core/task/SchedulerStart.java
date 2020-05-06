package com.example.demo.core.task;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.quartz.impl.jdbcjobstore.AttributeRestoringConnectionInvocationHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        //可以用addCalendar排除日期或规定个执行日期

        HolidayCalendar holidays = new HolidayCalendar();
        Calendar calendar = new GregorianCalendar(2020, 3, 30);
        holidays.addExcludedDate(calendar.getTime());
        holidays.setDescription("不允许2020-04-30执行");

        //WeeklyCalendar 可以用来排除每周星期几，默认WeeklyCalendar会排除星期六星期天
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        //排除星期五
        weeklyCalendar.setDayExcluded(Calendar.FRIDAY, true);
        weeklyCalendar.setDescription("不允许周五六七执行");


        //dailyCalendar用来排除每天不执行的时间段
        DailyCalendar dailyCalendar = new DailyCalendar("09:00:00","17:00:00");
        //使用setInvertTimeRange反转，可以规定可执行时间段
        dailyCalendar.setInvertTimeRange(true);
        dailyCalendar.setDescription("只允许9点到17点执行");


        scheduler.addCalendar("holidays", holidays, false, false);
        scheduler.addCalendar("dailyCalendar", dailyCalendar, false, false);
        scheduler.addCalendar("weeklydays", weeklyCalendar, false, false);


        //开启job
        JobDetail sampleJob = newJob(SampleJob.class).withIdentity("sampleJob").build();
        JobDetail weeklyJob = newJob(WeeklyJob.class).withIdentity("weeklyJob").build();
        JobDetail dayJob = newJob(DayJob.class).withIdentity("dayJob").build();

        //配置计划任务的定时器(触发器的作用应给job触发条件,同一个job可以添加多触发条件，不同的job也可以被相同的触发器触发)
        Trigger sampleTrigger = newTrigger()
                .withIdentity("sampleTrigger")
                .withSchedule(cronSchedule("*/30 * * * * ?"))
                .withDescription("0秒和30秒执行")
                .build();

        Trigger weeklyTrigger = newTrigger()
                .withIdentity("weeklyTrigger")
                .withSchedule(cronSchedule("0 0 9 * * ?"))
                .withDescription("每天9点")
                .modifiedByCalendar("weeklydays")
                .build();

        Trigger dailyTrigger = newTrigger()
                .withIdentity("dailyTrigger")
                .withSchedule(cronSchedule("0 0 0/1 * * ?"))
                .withDescription("整点执行一次")
                .modifiedByCalendar("dailyCalendar")
                .build();



        scheduler.scheduleJob(sampleJob, sampleTrigger);
        scheduler.scheduleJob(weeklyJob, weeklyTrigger);
        scheduler.scheduleJob(dayJob, dailyTrigger);
        scheduler.start();
    }

}
