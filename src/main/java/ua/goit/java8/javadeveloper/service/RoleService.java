package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.Role;

import java.util.List;


public interface RoleService {

    Role getById(Long id);

    List<Role> getAll();

    void create(Role role);

    void delete(Long id);

    void update(Role role);

    Role findByName(String name);

}
