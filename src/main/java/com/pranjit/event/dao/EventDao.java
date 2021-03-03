package com.pranjit.event.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pranjit.event.enums.ScheduleStatus;
import com.pranjit.event.entities.ScheduleVO;

@Repository
@Transactional
public class EventDao {
    @Autowired
    private SessionFactory factory;

    @Autowired
    private EntityManager em;

    //for creating session for hibernate connection to mysql
    public Session getSession() {

        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }

    public void saveEvent(ScheduleVO schedule) {

        getSession().save(schedule);
    }

    public List<ScheduleVO> getAllEvents() {

        Query q = getSession().createQuery("FROM ScheduleVO");
        List<ScheduleVO> allEvents = q.list();
        return allEvents;
    }

    public List<ScheduleVO> getAllEventsActive() {
        Query q = getSession().createQuery("FROM ScheduleVO s where s.status='" + ScheduleStatus.ACTIVE.name() + "'");
        List<ScheduleVO> allEvents = q.list();
        return allEvents;
    }

    public void updateEvent(ScheduleVO schedule) {

        getSession().saveOrUpdate(schedule);
    }

    public ScheduleVO getOneEvent(int eventId) {

        Query q = getSession().createQuery("FROM ScheduleVO where eventId=" + eventId);
        ScheduleVO oneEvent = (ScheduleVO) q.uniqueResult();
        return oneEvent;
    }

    public void deleteEvent(int id) {
        ScheduleVO scheduleVO = getOneEvent(id);
        getSession().delete(scheduleVO);

    }
    
}
