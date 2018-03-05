package ua.goit.java8.javadeveloper.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import ua.goit.java8.javadeveloper.report.entity.UserSalaryAggregation;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by t.oleksiv on 28/02/2018.
 */

public class CustomUserSalarySerializer extends JsonSerializer<UserSalaryAggregation> {

    @Autowired
    private SimpleDateFormat dateFormatter;

    @Override
    public void serialize(UserSalaryAggregation userSalaryAggregation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("userId", userSalaryAggregation.getUserId());
        jsonGenerator.writeStringField("startDate", dateFormatter.format(userSalaryAggregation.getStartDate()));
        jsonGenerator.writeStringField("endDate", dateFormatter.format(userSalaryAggregation.getEndDate()));
        jsonGenerator.writeStringField("username", userSalaryAggregation.getUsername());
        jsonGenerator.writeNumberField("salary", userSalaryAggregation.getSalary());
        jsonGenerator.writeEndObject();
    }
}
