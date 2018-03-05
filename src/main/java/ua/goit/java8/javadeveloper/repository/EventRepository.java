package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.goit.java8.javadeveloper.model.Event;

import java.util.Date;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

public interface EventRepository extends JpaRepository<Event,Long> {

    @Query(value = "SELECT e.id FROM events e where e.event_type = :id AND e.date = :date", nativeQuery = true)
    Long findByDateAndEventType(@Param("id") Long id, @Param("date") Date date);
}
