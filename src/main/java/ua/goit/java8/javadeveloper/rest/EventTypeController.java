package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.java8.javadeveloper.model.EventType;
import ua.goit.java8.javadeveloper.service.EventTypeService;

import java.util.List;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

@RestController
@RequestMapping(value = "api/admin/eventtypes")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    //-------------------Retrieve All Event Types--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        List<EventType> eventTypes = this.eventTypeService.getAll();

        if (eventTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(eventTypes, HttpStatus.OK);
    }
}
