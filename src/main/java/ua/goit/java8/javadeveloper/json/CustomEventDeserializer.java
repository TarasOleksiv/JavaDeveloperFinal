package ua.goit.java8.javadeveloper.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.EventTypeService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by t.oleksiv on 28/02/2018.
 */

@Component
public class CustomEventDeserializer extends JsonDeserializer<Event> {

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleDateFormat dateFormatter;

    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        Event event = new Event();
        // loop until token equal to "}"
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jsonParser.getCurrentName();

            if ("date".equals(fieldname)) {
                // current token is "date",
                // move to next, which is "date"'s value
                jsonParser.nextToken();
                try {
                    event.setDate(dateFormatter.parse(jsonParser.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //System.out.println(jsonParser.getText());
            }

            if ("duration".equals(fieldname)) {
                // current token is "duration",
                // move to next, which is "duration"'s value
                jsonParser.nextToken();
                event.setDuration(jsonParser.getIntValue());
                //System.out.println(jsonParser.getIntValue());
            }

            if ("eventType".equals(fieldname)) {
                // current token is "eventType",
                // move to next, which is "{"
                jsonParser.nextToken(); // current token is "{", move next
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    if ("id".equals(jsonParser.getCurrentName())) {
                        jsonParser.nextToken();
                        event.setEventType(eventTypeService.getById(jsonParser.getLongValue()));
                        //System.out.println(jsonParser.getLongValue());
                    }
                }
            }

            if ("users".equals(fieldname)) {
                Set<User> users = new HashSet<>();
                jsonParser.nextToken(); // current token is "[", move next
                // users is array, loop until token equal to "]"
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        if ("id".equals(jsonParser.getCurrentName())) {
                            jsonParser.nextToken();
                            User user = userService.getById(jsonParser.getLongValue());
                            if (user != null) users.add(user);
                            //System.out.println(jsonParser.getLongValue());
                        }
                    }
                }
                event.setUsers(users);
            }
        }

        jsonParser.close();
        return event;
    }
}
