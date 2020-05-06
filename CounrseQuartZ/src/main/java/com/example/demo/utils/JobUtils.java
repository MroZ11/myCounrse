package com.example.demo.utils;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2019/02/19.
 */
public class JobUtils {

    public static List<JobInfo> getAllJobsInfo(Scheduler scheduler) {
        List<JobInfo> jobInfos = new ArrayList<>();

        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    Trigger trigger = scheduler.getTriggersOfJob(jobKey).get(0);

                    JobInfo jobInfo = new JobInfo();
                    jobInfo.setJobName(jobKey.getName());
                    jobInfo.setJobGroup(jobKey.getGroup());
                    jobInfo.setJobDescription(jobDetail.getDescription());
                    jobInfo.setJobClass(jobDetail.getJobClass().toString());
                    jobInfo.setTriggerName(trigger.getKey().getName());
                    jobInfo.setTriggerGroup(trigger.getKey().getGroup());
                    jobInfo.setTriggerStatus(scheduler.getTriggerState(trigger.getKey()).toString());
                    jobInfo.setTriggerDescription(trigger.getDescription());
                    jobInfo.setTriggerStartTime(trigger.getStartTime());
                    jobInfo.setTriggerEndTime(trigger.getEndTime());
                    jobInfo.setTriggerPreviousFireTime(trigger.getPreviousFireTime());
                    jobInfo.setTriggerNextFireTime(trigger.getNextFireTime());
                    jobInfo.setTriggerCronExpression(trigger instanceof CronTriggerImpl ? ((CronTriggerImpl) trigger).getCronExpression() : null);
                    try {
                        //TODO 可能会有空指针之类的异常 为了方便 简单处理下
                        jobInfo.setCalendarName(trigger.getCalendarName());
                        jobInfo.setCalendarDesc(scheduler.getCalendar(trigger.getCalendarName()).getDescription());
                    }catch (Exception e){}

                    jobInfos.add(jobInfo);
                }
            }
        } catch (Exception e) {

        }
        return jobInfos;
    }

}