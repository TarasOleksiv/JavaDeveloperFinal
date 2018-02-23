package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.repository.RoleRepository;
import ua.goit.java8.javadeveloper.repository.UserRepository;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void create(User user) {

        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUserExist(User user) {
        return findByUsername(user.getUsername())!=null;
    }
}
