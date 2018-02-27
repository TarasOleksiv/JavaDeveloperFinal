package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.EventType;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface EventTypeService {

    EventType getById(Long id);

    void create(EventType eventType);

    void update(EventType eventType);

    void delete(Long id);

    List<EventType> getAll();

    EventType findByName(String name);

    boolean isEventTypeExist(EventType eventType);
}
