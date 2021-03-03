package com.pranjit.event.schedule;


import com.pranjit.event.dao.EventDao;
import com.pranjit.event.enums.ScheduleStatus;
import com.pranjit.event.entities.ScheduleVO;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ScheduleJob extends QuartzJobBean {
    @Autowired
    private EventDao eventDao;
    private static final Logger log= LoggerFactory.getLogger(ScheduleJob.class);
    @Override

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String name = jobDataMap.getString("name");
        String details = jobDataMap.getString("details");
        ScheduleVO schedule = (ScheduleVO) jobDataMap.get("schedule");
        if(schedule.getStatus()== ScheduleStatus.INACTIVE.name())
        {
            schedule.setStatus(ScheduleStatus.ACTIVE.name());
            eventDao.updateEvent(schedule);
        }
        log.info("event triggered for name ="+name+" with details = "+details);
        if(!schedule.isRepeatable())
        {
            schedule.setStatus(ScheduleStatus.COMPLETED.name());
            eventDao.updateEvent(schedule);


        }
    }
}
