package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.EventType;
import ua.goit.java8.javadeveloper.repository.EventRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 28.02.2018.
 */

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event getById(Long id) {
        return eventRepository.findOne(id);
    }

    @Override
    public void create(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        eventRepository.delete(id);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public boolean isEventExist(Event event) {
        return ((findByDate(event.getDate())!=null) && (findByEventType(event.getEventType())!=null));
    }

    @Override
    public Event findByDate(Date date) {
        return eventRepository.findByDate(date);
    }

    @Override
    public Event findByEventType(EventType eventType) {
        return eventRepository.findByEventType(eventType);
    }
}
