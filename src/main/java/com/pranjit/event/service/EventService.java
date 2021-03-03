package com.pranjit.event.service;

import com.pranjit.event.entities.ScheduleVO;

import java.sql.SQLException;
import java.util.List;

import org.quartz.SchedulerException;

public interface EventService {
    String createEvent(ScheduleVO category) throws SchedulerException;
    public List<ScheduleVO> getAllEvents() throws SQLException, ClassNotFoundException;
    public List<ScheduleVO> getAllEventsActive() throws SQLException, ClassNotFoundException;
    public ScheduleVO getOneEvent(int eventId) throws SQLException, ClassNotFoundException;
    String deleteEvent(int id);
}
