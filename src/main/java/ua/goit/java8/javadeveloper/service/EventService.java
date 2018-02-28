package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.EventType;

import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface EventService {

    Event getById(Long id);

    void create(Event event);

    void delete(Long id);

    List<Event> getAll();

    boolean isEventExist(Event event);

    Event findByDate(Date date);

    Event findByEventType(EventType eventType);

}
