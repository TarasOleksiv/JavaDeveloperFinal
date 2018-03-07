package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.UserService;
import ua.goit.java8.javadeveloper.validator.RoleUserValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 20.02.2018.
 */

@RestController
@RequestMapping(value = "api/moderator/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUserValidator roleUserValidator;

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //-------------------Retrieve Single User (integrated security)--------------------------------------------------------

    @RequestMapping(value = {"/{id}", "/{id}/hasuseraccess"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") long id, Authentication authentication) {

        System.out.println("Fetching User with id " + id);
        User user = userService.getById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        if (!roleUserValidator.isAllowedToRead(id, authentication)){
            Map<String, String> messages = new HashMap<String, String>();
            messages.put("error", "Access Denied");
            return new ResponseEntity<>(messages, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        Map<String, String> messages = checkUser(user);
        if (!messages.isEmpty()){
            System.out.println("Wrong User input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        String username = user.getUsername();
        System.out.println("Creating User " + username);

        if (userService.isUserExist(user)) {
            String errorMessage = "A User with username " + username + " already exists";
            System.out.println(errorMessage);
            messages.put("error", errorMessage);
            return new ResponseEntity<>(messages, HttpStatus.CONFLICT);
        }

        userService.create(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        Map<String, String> messages = checkUser(user);
        if (!messages.isEmpty()){
            System.out.println("Wrong User input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        User currentUser = userService.getById(id);

        if (currentUser==null) {
            String errorMessage = "User with id " + id + " not found";
            System.out.println(errorMessage);
            messages.put("error",errorMessage);
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        }

        if (userService.isUserExist(user)){
            String username = user.getUsername();
            if (userService.findByUsername(username).getId() != id) {
                String errorMessage = "A User with username " + username + " already exists";
                System.out.println(errorMessage);
                messages.put("error", errorMessage);
                return new ResponseEntity<>(messages, HttpStatus.CONFLICT);
            }
        }

        currentUser.setUsername(user.getUsername());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setHourly_rate(user.getHourly_rate());

        userService.update(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.getById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    //---------------------- User validation --------------------------------------------
    private Map<String, String> checkUser(User user){
        Map<String, String> messages = new HashMap<String, String>();

        String username = user.getUsername();
        if (username == null || username.trim().isEmpty()){
            messages.put("username","null or empty");
        } else {
            if (username.length() < 4 || username.length() > 20){
                messages.put("username","username's length should be greater than 3 and less than 21");
            }
        }

        String password = user.getPassword();
        if (password == null || password.trim().isEmpty()){
            messages.put("password","null or empty");
        } else {
            if (password.length() < 8 || password.length() > 32){
                messages.put("password","password's length should be greater than 7 and less than 33");
            }
        }

        //String hourly_rate = user.getHourly_rate().toString();
        if (user.getHourly_rate() == null) {
            messages.put("hourly_rate", "null or empty");
        } else if (!user.getHourly_rate().toString().matches("^[0-9]*[.]?[0-9]+$")) {
            messages.put("hourly_rate", "Please enter digits and/or dot only");
        }

        return messages;
    }

}
