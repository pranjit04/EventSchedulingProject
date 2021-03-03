package com.pranjit.event.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pranjit.event.enums.RepeatationType;
import com.pranjit.event.enums.ScheduleStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedule")
public class ScheduleVO implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eventId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date endDateTime;
    private boolean repeatable;
    private String repeatationType;
    private String repeationFrequency;
    private String days; //comma separated
    private String status;
    private String eventName;
    private String eventDetails;


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public String getRepeatationType() {
        return repeatationType;
    }

    public void setRepeatationType(String repeatationType) {
        this.repeatationType = repeatationType;
    }

    public String getRepeationFrequency() {
        return repeationFrequency;
    }

    public void setRepeationFrequency(String repeationFrequency) {
        this.repeationFrequency = repeationFrequency;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }





    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }


}
