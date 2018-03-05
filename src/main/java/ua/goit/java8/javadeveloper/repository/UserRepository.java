package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.goit.java8.javadeveloper.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "SELECT eu.user_id FROM event_users eu RIGHT JOIN events e ON eu.event_id = e.id WHERE e.date = :date AND eu.user_id = :id", nativeQuery = true)
    List<Long> findByUserIdAndEventDate(@Param("id") Long id, @Param("date") Date date);
}
