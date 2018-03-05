package ua.goit.java8.javadeveloper.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * Created by t.oleksiv on 01/03/2018.
 */

@Configuration
public class DateFormatter {

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
