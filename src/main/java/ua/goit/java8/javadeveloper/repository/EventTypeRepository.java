package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.java8.javadeveloper.model.EventType;

/**
 * Created by t.oleksiv on 27/02/2018.
 */
public interface EventTypeRepository extends JpaRepository<EventType,Long> {
    EventType findByName(String name);
}
