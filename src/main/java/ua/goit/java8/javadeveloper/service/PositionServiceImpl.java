package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.Position;
import ua.goit.java8.javadeveloper.repository.PositionRepository;

import java.util.List;

/**
 * Created by Taras on 23.02.2018.
 */

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Position getById(Long id) {
        return positionRepository.findOne(id);
    }

    @Override
    public void create(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void update(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void delete(Long id) {
        positionRepository.delete(id);
    }

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findByName(String name) {
        return positionRepository.findByName(name);
    }

    @Override
    public boolean isPositionExist(Position position) {
        return findByName(position.getName())!=null;
    }
}
