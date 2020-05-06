package com.example.demo.controller;

import com.example.demo.utils.JobInfo;
import com.example.demo.utils.JobUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.calendar.DailyCalendar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by cloud on 2020/4/30.
 */
@Controller
public class TestController {

    @Resource
    Scheduler scheduler;


    @GetMapping("taskList")
    public String taskList(ModelMap modelMap) {
        List<JobInfo> jobInfos = JobUtils.getAllJobsInfo(scheduler);
        modelMap.put("taskList", jobInfos);
        return "taskList";
    }

    @RequestMapping("parse/{name}")
    public String parse(@PathVariable("name") String name) {
        try {
            scheduler.pauseJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/taskList";
    }

    @RequestMapping("resume/{name}")
    public String resume(@PathVariable("name") String name) {
        try {
            scheduler.resumeJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/taskList";
    }


    @RequestMapping("runnow/{name}")
    public String runnow(@PathVariable("name") String name) {
        try {
            Trigger trigger = newTrigger().forJob(name).startNow().build();
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/taskList";
    }

    @RequestMapping("delete/{name}")
    public String delete(@PathVariable("name") String name) {
        try {
            scheduler.deleteJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/taskList";
    }

    @RequestMapping("setDailyCalendar")
    public String delete(String startHour, String endHour) {
        //dailyCalendar用来排除每天不执行的时间段
        DailyCalendar dailyCalendar = new DailyCalendar(startHour + ":00:00", endHour + ":00:00");
        dailyCalendar.setInvertTimeRange(true);
        dailyCalendar.setDescription("只允许" + startHour + "点到" + endHour + "点执行");

        try {
            System.out.println(scheduler.getContext().get("TriggerContext"));
            System.out.println(scheduler.getContext().get("triggerContext"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            scheduler.addCalendar("dailyCalendar", dailyCalendar, true, true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "redirect:/taskList";
    }


}
