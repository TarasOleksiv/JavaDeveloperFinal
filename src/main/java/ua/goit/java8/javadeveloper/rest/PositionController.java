package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.goit.java8.javadeveloper.model.Position;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.PositionService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 24.02.2018.
 */

@RestController
@RequestMapping(value = "api/admin/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Positions--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = this.positionService.getAll();

        if (positions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    //-------------------Retrieve Single Position--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Position> getPosition(@PathVariable("id") long id) {
        System.out.println("Fetching Position with id " + id);
        Position position = positionService.getById(id);
        if (position == null) {
            System.out.println("Position with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    //-------------------Create a Position--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createPosition(@RequestBody Position position, UriComponentsBuilder ucBuilder) {

        if (!checkPosition(position)){
            System.out.println("Wrong Position input!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println("Creating Position " + position.getName());

        if (positionService.isPositionExist(position)) {
            System.out.println("A Position with name " + position.getName() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        positionService.create(position);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(position.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //------------------- Update a Position --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Position> updatePosition(@PathVariable("id") long id, @RequestBody Position position) {
        System.out.println("Updating Position " + id);

        Position currentPosition = positionService.getById(id);

        if (currentPosition==null) {
            System.out.println("Position with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!checkPosition(position)){
            System.out.println("Wrong Position input!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (positionService.isPositionExist(position)){
            if (positionService.findByName(position.getName()).getId() != id) {
                System.out.println("A Position with name " + position.getName() + " already exists");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        currentPosition.setName(position.getName());

        positionService.update(currentPosition);
        return new ResponseEntity<>(currentPosition, HttpStatus.OK);
    }

    //------------------- Delete a Position --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Position> deletePosition(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Position with id " + id);

        Position position = positionService.getById(id);
        if (position == null) {
            System.out.println("Unable to delete. Position with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        positionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-------------------Retrieve Users for the Department --------------------------------------------------------

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getPositionUsers(@PathVariable("id") long id) {
        System.out.println("Fetching Users for position_id " + id);
        Position position = positionService.getById(id);
        if (position == null) {
            System.out.println("Position with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> users = new ArrayList<>(position.getUsers());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //------------------- Add User onto Position --------------------------------------------------------

    @RequestMapping(value = "/{position_id}/adduser/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addUserOnPosition(@PathVariable("position_id") long position_id, @PathVariable("user_id") long user_id, UriComponentsBuilder ucBuilder) {
        System.out.println("Adding user with id " +  user_id + " onto Position with id " + position_id);

        Position position = positionService.getById(position_id);
        if (position == null) {
            System.out.println("Unable to add. Position with id " + position_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.getById(user_id);
        if (user == null) {
            System.out.println("Unable to add. User with id " + user_id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (position.isUserExist(user)){
            System.out.println("A Position with name " + position.getName() + " already has user " + user.getUsername());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        Position oldPosition = user.getPosition();
        if ((oldPosition == null) || (oldPosition.getId() != position.getId())){

            user.setPosition(position);
            userService.updatePure(user);
        }

        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/{department_id}/users").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    //--------------------------- Position validation ------------------------------------------------------------
    private boolean checkPosition(Position position){

        if (position.getName()==null || position.getName().trim().isEmpty()){
            return false;
        }

        return true;
    }

}
