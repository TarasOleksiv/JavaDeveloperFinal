package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.Department;
import ua.goit.java8.javadeveloper.model.Position;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface PositionService {

    Position getById(Long id);

    void create(Position position);

    void update(Position position);

    void delete(Long id);

    List<Position> getAll();

    Position findByName(String name);

    boolean isPositionExist(Position position);
}
