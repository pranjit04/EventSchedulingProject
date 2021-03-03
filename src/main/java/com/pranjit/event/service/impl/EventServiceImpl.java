package com.pranjit.event.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjit.event.dao.EventDao;
import com.pranjit.event.enums.RepeatationType;
import com.pranjit.event.enums.ScheduleStatus;
import com.pranjit.event.entities.ScheduleVO;
import com.pranjit.event.schedule.ScheduleHelper;
import com.pranjit.event.service.EventService;

@Service


public class EventServiceImpl  implements EventService{
    @Autowired
     EventDao eventDao;
    @Autowired
    ScheduleHelper scheduleHelper;
    @Autowired
    private Scheduler scheduler;

    /**
     * This method makes events in the scheduler persistent, means if server is restarted all
     * the active events in the scheduler gets active again
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @PostConstruct
    public void triggerEventsOnStartup() throws ClassNotFoundException, SQLException {
    	List<ScheduleVO> allEvents = eventDao.getAllEventsActive();
    	allEvents.forEach(schedule -> {
			try {
				buildScheduleJob(schedule);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		});
    }
    
    public String createEvent(ScheduleVO schedule) throws SchedulerException {
        schedule.setStatus(ScheduleStatus.INACTIVE.name());
        eventDao.saveEvent(schedule );
        buildScheduleJob(schedule);
        return "save successfully";

    }

    /**
     * The method schedules the job after the event is save and perform accordingly with the condition either scheduling event is repeatable or not
     * @param schedule
     * @throws SchedulerException
     */
	private void buildScheduleJob(ScheduleVO schedule) throws SchedulerException {
		JobDetail jobDetails = scheduleHelper.buildJobDetail(schedule);
        Trigger trigger=null;
        if(schedule.isRepeatable())
        {
        	if(RepeatationType.DAYS_OF_WEEK.name().equals(schedule.getRepeatationType()) && StringUtils.isNotEmpty(schedule.getDays())) {
        		String cronExpression = "0 0 12 ? * "+schedule.getDays()+" *";
        		trigger = scheduleHelper.buildJobTriggerMultiple(jobDetails, schedule.getStartDateTime(),schedule.getEndDateTime(), cronExpression);
        	} else if(RepeatationType.FREQUENCY.name().equals(schedule.getRepeatationType())) {
                trigger = scheduleHelper.buildJobTrigger(jobDetails, schedule.getStartDateTime(),schedule.getEndDateTime(), schedule.getRepeationFrequency());
        	}
        }
        else {
             trigger = scheduleHelper.buildJobTrigger(jobDetails, schedule.getStartDateTime(), schedule.getStartDateTime(),null);

        }

        scheduler.scheduleJob(jobDetails,trigger);
	}
    public List<ScheduleVO> getAllEvents() throws SQLException, ClassNotFoundException{
        List<ScheduleVO> allEvents = eventDao.getAllEvents();
        return allEvents;

    }
    public List<ScheduleVO> getAllEventsActive() throws SQLException, ClassNotFoundException{
        List<ScheduleVO> allEventsActive = eventDao.getAllEventsActive();
        return allEventsActive;


    }

    public ScheduleVO getOneEvent(int eventId) throws SQLException, ClassNotFoundException {
        ScheduleVO oneEvent = eventDao.getOneEvent(eventId);
        if(oneEvent==null)
        {
            throw new RuntimeException("Event Id is not valid.");
        }
        return(oneEvent);

    }
    public String deleteEvent(int id) {
        eventDao.deleteEvent(id);
        return "deleted successfully";

    }
	


}
