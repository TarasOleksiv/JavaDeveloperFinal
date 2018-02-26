package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.java8.javadeveloper.model.Department;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}
