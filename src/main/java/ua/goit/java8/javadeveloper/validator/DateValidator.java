package ua.goit.java8.javadeveloper.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by t.oleksiv on 02/03/2018.
 */

@Component
public class DateValidator {

    @Autowired
    private SimpleDateFormat dateFormatter;

    //---------------- Check date pattern 'yyyy-MM-dd' ---------------------------------------

    public boolean isThisDateValid(String dateToValidate){

        if(dateToValidate == null){
            return false;
        }

        if (!checkDatePattern(dateToValidate, "^\\d\\d\\d\\d-\\d\\d-\\d\\d$")){
            return false;
        }

        dateFormatter.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = dateFormatter.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    //---------------- Check year pattern 'yyyy' ---------------------------------------

    public boolean isThisYearValid(String dateToValidate){
        if(dateToValidate == null){
            return false;
        }

        if (!checkDatePattern(dateToValidate, "^\\d\\d\\d\\d$")){
            return false;
        }

        return true;
    }

    //---------------- Check month pattern 'MM' ---------------------------------------

    public boolean isThisMonthValid(String dateToValidate){
        if(dateToValidate == null){
            return false;
        }

        if (!checkDatePattern(dateToValidate, "^\\d\\d$")){
            return false;
        }

        if (Integer.parseInt(dateToValidate) > 12){
            return false;
        }

        return true;
    }

    //-----------------------------------------------------------------------------------

    private boolean checkDatePattern(String text, String patternString){
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(text);
        boolean matches = matcher.matches();
        return matches;
    }

}