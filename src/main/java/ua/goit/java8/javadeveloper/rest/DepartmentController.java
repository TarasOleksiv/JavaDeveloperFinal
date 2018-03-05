package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.goit.java8.javadeveloper.model.Department;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.DepartmentService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 24.02.2018.
 */

@RestController
@RequestMapping(value = "api/moderator/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Departments--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = this.departmentService.getAll();

        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    //-------------------Retrieve Single Department--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Department> getDepartment(@PathVariable("id") long id) {
        System.out.println("Fetching Department with id " + id);
        Department department = departmentService.getById(id);
        if (department == null) {
            System.out.println("Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    //-------------------Create a Department--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartment(@RequestBody Department department, UriComponentsBuilder ucBuilder) {

        Map<String, String> messages = checkDepartment(department);
        if (!messages.isEmpty()){
            System.out.println("Wrong Department input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        String name = department.getName();
        System.out.println("Creating Department " + name);

        if (departmentService.isDepartmentExist(department)) {
            String errorMessage = "A Department with name " + name + " already exists";
            System.out.println(errorMessage);
            messages.put("error",errorMessage);
            return new ResponseEntity<>(messages,HttpStatus.CONFLICT);
        }

        departmentService.create(department);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/moderator/departments/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<Department>(headers, HttpStatus.CREATED);
    }

    //------------------- Update a Department --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartment(@PathVariable("id") long id, @RequestBody Department department, UriComponentsBuilder ucBuilder) {
        System.out.println("Updating Department " + id);

        Map<String, String> messages = checkDepartment(department);
        if (!messages.isEmpty()){
            System.out.println("Wrong Department input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        Department currentDepartment = departmentService.getById(id);

        if (currentDepartment==null) {
            String errorMessage = "Department with id " + id + " not found";
            System.out.println(errorMessage);
            messages.put("error",errorMessage);
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        }

        if (departmentService.isDepartmentExist(department)){
            String name = department.getName();
            if (departmentService.findByName(name).getId() != id) {
                String errorMessage = "A Department with name " + name + " already exists";
                System.out.println(errorMessage);
                messages.put("error",errorMessage);
                return new ResponseEntity<>(messages, HttpStatus.CONFLICT);
            }
        }

        currentDepartment.setName(department.getName());

        departmentService.update(currentDepartment);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/moderator/departments/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<>(currentDepartment, headers, HttpStatus.OK);
    }

    //------------------- Delete a Department --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Department with id " + id);

        Department department = departmentService.getById(id);
        if (department == null) {
            System.out.println("Unable to delete. Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        departmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-------------------Retrieve Users for the Department --------------------------------------------------------

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getDepartmentUsers(@PathVariable("id") long id) {
        System.out.println("Fetching Users for department_id " + id);
        Department department = departmentService.getById(id);
        if (department == null) {
            System.out.println("Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> users = new ArrayList<>(department.getUsers());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //------------------- Add User to Department --------------------------------------------------------

    @RequestMapping(value = "/{department_id}/adduser/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addUserToDepartment(@PathVariable("department_id") long department_id, @PathVariable("user_id") long user_id, UriComponentsBuilder ucBuilder) {
        System.out.println("Adding user with id " +  user_id + " to Department with id " + department_id);

        Department department = departmentService.getById(department_id);
        if (department == null) {
            System.out.println("Unable to add. Department with id " + department_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.getById(user_id);
        if (user == null) {
            System.out.println("Unable to add. User with id " + user_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (department.isUserExist(user)){
            System.out.println("A Department with name " + department.getName() + " already has user " + user.getUsername());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        Department oldDepartment = user.getDepartment();
        if ((oldDepartment == null) || (oldDepartment.getId() != department.getId())){

            user.setDepartment(department);
            userService.updatePure(user);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/moderator/departments/{department_id}/users").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    //----------------------------- Department validation --------------------------------------------------------
    private Map<String, String> checkDepartment(Department department){
        Map<String, String> messages = new HashMap<String, String>();
        String name = department.getName();
        if (name == null || name.trim().isEmpty()){
            messages.put("name","null or empty");
        }

        return messages;
    }
}
