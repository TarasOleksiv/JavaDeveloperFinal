package ua.goit.java8.javadeveloper.service;

import ua.goit.java8.javadeveloper.model.Department;

import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */
public interface DepartmentService {

    Department getById(Long id);

    void create(Department department);

    void update(Department department);

    void delete(Long id);

    List<Department> getAll();

    Department findByName(String name);

    boolean isDepartmentExist(Department department);
}
