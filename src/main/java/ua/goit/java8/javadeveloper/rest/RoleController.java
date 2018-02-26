package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ua.goit.java8.javadeveloper.model.Role;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.RoleService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 20.02.2018.
 */

@RestController
@RequestMapping(value = "api/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Roles--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = this.roleService.getAll();

        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    //-------------------Retrieve Users for the Role --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getRoleUsers(@PathVariable("id") long id) {
        System.out.println("Fetching Users with role_id " + id);
        Role role = roleService.getById(id);
        if (role == null) {
            System.out.println("Role with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> users = new ArrayList<>(role.getUsers());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    //------------------- Add Role to a User --------------------------------------------------------

    @RequestMapping(value = "/{role_id}/add_user/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addRoleToUser(@PathVariable("role_id") long role_id, @PathVariable("user_id") long user_id, UriComponentsBuilder ucBuilder) {
        System.out.println("Adding user with id " +  user_id + " to Role with id " + role_id);

        Role role = roleService.getById(role_id);
        if (role == null) {
            System.out.println("Unable to add. Role with id " + role_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.getById(user_id);
        if (user == null) {
            System.out.println("Unable to add. User with id " + user_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        if (userService.addRole(user,role)){
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

    }

    //------------------- Remove Role from a User --------------------------------------------------------

    @RequestMapping(value = "/{role_id}/remove_user/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable("role_id") long role_id, @PathVariable("user_id") long user_id, UriComponentsBuilder ucBuilder) {
        System.out.println("Removing user with id " +  user_id + " from Role with id " + role_id);

        Role role = roleService.getById(role_id);
        if (role == null) {
            System.out.println("Unable to remove. Role with id " + role_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.getById(user_id);
        if (user == null) {
            System.out.println("Unable to remove. User with id " + user_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        if (userService.removeRole(user,role)){
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

}
