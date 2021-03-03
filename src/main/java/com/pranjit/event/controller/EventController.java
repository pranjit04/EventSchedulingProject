package com.pranjit.event.controller;

import java.sql.SQLException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pranjit.event.entities.ScheduleVO;
import com.pranjit.event.service.EventService;

@RestController
@RequestMapping("/events")


public class EventController {
    @Autowired
    private EventService eventService;

    /**
     * This REST end point is designed to save a scheduled event to be run of a particular time period.
     * @param schedule
     * @return a message confirming completion of saving of the event to be scheduled.
     * @throws SchedulerException
     */
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createEvent(@RequestBody ScheduleVO schedule) throws SchedulerException {
        String message = eventService.createEvent(schedule);
        return message;
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteEvent(@RequestParam int id)
    {
        String message = eventService.deleteEvent(id);
        return message;


    }

    @GetMapping(path = "event/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleVO> getAllEvents() throws SQLException, ClassNotFoundException{
        List<ScheduleVO> eventList= eventService.getAllEvents();
        return eventList;
    }

    @GetMapping(path = "/event/getOne/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleVO getOneEvent(@PathVariable int eventId) throws SQLException, ClassNotFoundException{
        ScheduleVO oneEvent= eventService.getOneEvent(eventId);
        return oneEvent;
    }
    @GetMapping(path = "event/getAll/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleVO> getAllEventsActive() throws SQLException, ClassNotFoundException{
        List<ScheduleVO> eventList= eventService.getAllEventsActive();
        return eventList;
    }

}
