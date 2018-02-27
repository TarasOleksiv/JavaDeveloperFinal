package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.EventType;
import ua.goit.java8.javadeveloper.repository.EventTypeRepository;

import java.util.List;

/**
 * Created by Taras on 23.02.2018.
 */

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Override
    public EventType getById(Long id) {
        return eventTypeRepository.findOne(id);
    }

    @Override
    public void create(EventType eventType) {
        eventTypeRepository.save(eventType);
    }

    @Override
    public void update(EventType eventType) {
        eventTypeRepository.save(eventType);
    }

    @Override
    public void delete(Long id) {
        eventTypeRepository.delete(id);
    }

    @Override
    public List<EventType> getAll() {
        return eventTypeRepository.findAll();
    }

    @Override
    public EventType findByName(String name) {
        return eventTypeRepository.findByName(name);
    }

    @Override
    public boolean isEventTypeExist(EventType eventType) {
        return findByName(eventType.getName())!=null;
    }

}
