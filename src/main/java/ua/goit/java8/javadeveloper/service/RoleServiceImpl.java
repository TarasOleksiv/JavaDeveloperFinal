package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.Role;
import ua.goit.java8.javadeveloper.repository.RoleRepository;

import java.util.List;

/**
 * Created by t.oleksiv on 07/02/2018.
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Long id) { return roleRepository.findOne(id); }

    @Override
    public List<Role> getAll() { return roleRepository.findAll(); }

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(id);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
