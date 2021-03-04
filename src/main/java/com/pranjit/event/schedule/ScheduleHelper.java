package com.pranjit.event.schedule;


import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import com.pranjit.event.entities.ScheduleVO;

@Component
public class ScheduleHelper {

    
    /**
     * Based on the scheduled information from user, create the job details to perform the operation when the job is triggered.
     * @param schedulerVO
     * @return an instance of job builder
     */
    public JobDetail buildJobDetail(ScheduleVO schedulerVO) {
        JobDataMap jobDataMap = new JobDataMap(); // JobDataMap is used to store jobs in the scheduler
        String eventName= schedulerVO.getEventName();
        String eventDetails= schedulerVO.getEventDetails();
        jobDataMap.put("name",eventName);
        jobDataMap.put("details",eventDetails);
        jobDataMap.put("schedule",schedulerVO);

        return JobBuilder.newJob(ScheduleJob.class) //It is a class of quartz to create a job and build the job
                .withIdentity(UUID.randomUUID().toString(), "my-jobs")
                .withDescription(" Running Schedule Jobs")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    /**
     * Create the trigger to specify the time period when the job should be triggered.
     * @param jobDetail
     * @param startDateTime
     * @param repeatationFrequency
     * @return an instance of the trigger.
     */
    public Trigger buildJobTrigger(JobDetail jobDetail, Date startDateTime, Date endDateTime, String repeatationFrequency) {
    	TriggerBuilder<Trigger> trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "triggers")
                .withDescription("Trigger")
                .startAt(startDateTime)
                .endAt(endDateTime);
        if(StringUtils.isEmpty(repeatationFrequency)) {
            trigger.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow());
        } else if(repeatationFrequency.endsWith("hours")) {
        	trigger.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(Integer.parseInt(repeatationFrequency.substring(0, repeatationFrequency.length()-5))).repeatForever());
        } else if(repeatationFrequency.endsWith("minutes")) {
        	trigger.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(Integer.parseInt(repeatationFrequency.substring(0, repeatationFrequency.length()-7))).repeatForever());
        }
        return trigger.build();
    }


    public Trigger buildJobTriggerMultiple(JobDetail jobDetail, Date startDateTime, Date endDateTime, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "triggers")
                .withDescription("Trigger")
                .startAt(startDateTime)
                .endAt(endDateTime)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
