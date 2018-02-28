package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.service.EventService;

import java.util.List;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

@RestController
@RequestMapping(value = "api/admin/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //-------------------Retrieve All Events--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = this.eventService.getAll();

        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
