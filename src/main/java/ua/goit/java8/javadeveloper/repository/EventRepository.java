package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.EventType;

import java.util.Date;

/**
 * Created by t.oleksiv on 27/02/2018.
 */
public interface EventRepository extends JpaRepository<Event,Long> {

    Event findByDate(Date date);

    Event findByEventType(EventType eventType);

}
