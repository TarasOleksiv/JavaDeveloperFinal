package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.User;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface UserService {

    User getById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);

    List<User> getAll();

    User findByUsername(String username);
}
