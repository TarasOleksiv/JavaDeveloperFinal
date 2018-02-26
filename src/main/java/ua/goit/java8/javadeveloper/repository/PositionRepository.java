package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.java8.javadeveloper.model.Position;


public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByName(String name);
}
