package com.pranjit.event.controller;

import java.sql.SQLException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.pranjit.event.entities.ScheduleVO;
import com.pranjit.event.service.EventService;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/events")


public class EventController {
    @Autowired
    private EventService eventService;
    /**
     * This REST end point is designed to save a scheduled event to be run of a particular time period.
     *
     * @param schedule
     * @return a message confirming completion of saving of the event to be scheduled.
     * @throws SchedulerException
     */
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createEvent(@RequestBody ScheduleVO schedule) throws SchedulerException {
        String message = eventService.createEvent(schedule);
        return message;
    }
    /**
     * This REST end point is designed to delete or cancel a scheduled event .
     *
     * @param id
     * @return a message confirming deletion of the event.
     */
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteEvent(@RequestParam int id) {
        String message = eventService.deleteEvent(id);
        return message;
    }
    /**
     * This REST end point is designed to get details of all  events in the scheduler.
     *
     * @return A list of details of the all  events.
     */
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleVO> getAllEvents() throws SQLException, ClassNotFoundException {
        List<ScheduleVO> eventList = eventService.getAllEvents();
        return eventList;
    }
    /**
     * @param eventId This REST end point is designed to get details of one individual  events in the scheduler.
     * @return A list of details of the one individual events.
     */
    @GetMapping(path = "/getOne/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleVO getOneEvent(@PathVariable int eventId) throws SQLException, ClassNotFoundException {
        ScheduleVO oneEvent = eventService.getOneEvent(eventId);
        return oneEvent;
    }
    /**
     * This REST end point is designed to get details of all active events in the scheduler.
     *
     * @return A list of details of the all active events.
     */
    @GetMapping(path = "/getAll/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleVO> getAllEventsActive() throws SQLException, ClassNotFoundException {
        List<ScheduleVO> eventList = eventService.getAllEventsActive();
        return eventList;
    }
    @PutMapping(path = "/cancel/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String cancelEvent(@PathVariable int eventId) throws ValidationException, SQLException, ClassNotFoundException {
         eventService.cancelEvent(eventId);
         return "event cancelled successfully";
    }
}
