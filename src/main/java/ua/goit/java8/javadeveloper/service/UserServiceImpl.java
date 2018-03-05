package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.Role;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.repository.DepartmentRepository;
import ua.goit.java8.javadeveloper.repository.RoleRepository;
import ua.goit.java8.javadeveloper.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private DepartmentRepository departmentRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void create(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void update(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    @Override
    public void updatePure(User user){
        userRepository.save(user);
    }

    @Override
    public boolean addRole(User user, Role role) {
        Set<Role> roles = new HashSet<>();
        roles = user.getRoles();
        if (roles.add(role)){
            user.setRoles(roles);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRole(User user, Role role) {
        Set<Role> roles = new HashSet<>();
        roles = user.getRoles();
        if (roles.remove(role)){
            user.setRoles(roles);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<Long> findByUserIdAndEventDate(Long id, Date date) {
        return userRepository.findByUserIdAndEventDate(id,date);
    }

}
