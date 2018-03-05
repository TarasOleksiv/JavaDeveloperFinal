package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.EventService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

@RestController
@RequestMapping(value = "api/moderator/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SimpleDateFormat dateFormatter;

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Events--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = this.eventService.getAll();

        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    //-------------------Retrieve Single Event--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Event> getEvent(@PathVariable("id") long id) {
        System.out.println("Fetching Event with id " + id);
        Event event = eventService.getById(id);
        if (event == null) {
            System.out.println("Event with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

//-------------------Create Event--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(@RequestBody Event event, UriComponentsBuilder ucBuilder) {

        Map<String, String> messages = checkEvent(event);
        if (!messages.isEmpty()){
            System.out.println("Wrong Event input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        System.out.println("Creating Event ... ");

        if (eventService.findByDateAndEventType(event.getEventType().getId(),event.getDate()) != null){
            String errorMessage = "Event for date " + dateFormatter.format(event.getDate()) + " and of type '" + event.getEventType().getName() + "' already exists";
            System.out.println(errorMessage);
            messages.put("error",errorMessage);
            return new ResponseEntity<>(messages,HttpStatus.CONFLICT);
        }

        Set<User> users = event.getUsers();
        Long id;
        Date date = event.getDate();
        for (User user: users){
            id = user.getId();
            List<Long> ids = userService.findByUserIdAndEventDate(id, date);
            if ((ids != null) && (!ids.isEmpty())){
                messages.put("userId "+id, "User with id " + id + " is involved already into another event on this day '" + dateFormatter.format(date) + "'");
            }
        }

        if (!messages.isEmpty()){
            return new ResponseEntity<>(messages,HttpStatus.CONFLICT);
        }

        eventService.create(event);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/admin/events/{id}").buildAndExpand(event.getId()).toUri());
        return new ResponseEntity<Event>(headers, HttpStatus.CREATED);

    }

    //------------------- Delete Event --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Event with id " + id);

        Event event = eventService.getById(id);
        if (event == null) {
            System.out.println("Unable to delete. Event with id " + id + " not found");
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        eventService.delete(id);
        return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
    }

    //----------------------------- Event validation --------------------------------------------------------
    private Map<String, String> checkEvent(Event event){
        Map<String, String> messages = new HashMap<String, String>();

        if (event.getDate() == null){
            messages.put("date","null or empty or not valid");
        }

        if (event.getDuration() == null){
            messages.put("duration","null or empty");
        }

        if (event.getEventType() == null){
            messages.put("eventType","null or empty");
        }

        if ((event.getUsers() == null) || (event.getUsers().isEmpty())){
            messages.put("users","null or empty");
        }

        return messages;
    }

}
