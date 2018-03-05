package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.Role;
import ua.goit.java8.javadeveloper.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface UserService {

    User getById(Long id);

    void create(User user);

    void update(User user);

    void updatePure(User user);

    void delete(Long id);

    List<User> getAll();

    User findByUsername(String username);

    boolean isUserExist(User user);

    boolean addRole(User user, Role role);

    boolean removeRole(User user, Role role);

    List<Long> findByUserIdAndEventDate(Long id, Date date);

}
