package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.java8.javadeveloper.model.User;

/**
 * Created by Taras on 20.02.2018.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
