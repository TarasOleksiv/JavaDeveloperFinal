package ua.goit.java8.javadeveloper.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ua.goit.java8.javadeveloper.model.Event;
import ua.goit.java8.javadeveloper.model.User;

import java.io.IOException;

/**
 * Created by t.oleksiv on 28/02/2018.
 */

public class CustomEventSerializer extends JsonSerializer<Event> {

    @Override
    public void serialize(Event event, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", event.getId());
        jsonGenerator.writeStringField("date", event.getDate().toString());
        jsonGenerator.writeNumberField("duration", event.getDuration());

        jsonGenerator.writeObjectFieldStart("eventType");
        jsonGenerator.writeObjectField("id", event.getEventType().getId());
        jsonGenerator.writeObjectField("name", event.getEventType().getName());
        jsonGenerator.writeEndObject();

        if (event.getUsers() != null) {
            jsonGenerator.writeArrayFieldStart("users");
            for(User user: event.getUsers()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", user.getId());
                jsonGenerator.writeStringField("username", user.getUsername());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}
