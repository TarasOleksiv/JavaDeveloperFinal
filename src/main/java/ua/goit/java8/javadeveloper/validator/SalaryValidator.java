package ua.goit.java8.javadeveloper.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.UserSalaryService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t.oleksiv on 03/03/2018.
 */

@Component
public class SalaryValidator {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private DateValidator dateValidator;

    public Map<String, String> checkPeriodInput(long id, String startDateStr, String endDateStr){
        Map<String, String> messages = new HashMap<String, String>();

        User user = userService.getById(id);
        if (user == null) {
            String errorMessage = "User with id " + id + " not found";
            System.out.println(errorMessage);
            messages.put("error id",errorMessage);
        }

        if (!dateValidator.isThisDateValid(startDateStr)){
            String errorMessage = "Not a valid date: '" + startDateStr + "'";
            System.out.println(errorMessage);
            messages.put("error start date",errorMessage);
        }

        if (!dateValidator.isThisDateValid(endDateStr)){
            String errorMessage = "Not a valid date: '" + endDateStr + "'";
            System.out.println(errorMessage);
            messages.put("error end date",errorMessage);
        }

        return messages;
    }

    public Map<String, String> checkUserYearMonthInput(long id, String year, String month){
        Map<String, String> messages = new HashMap<String, String>();

        User user = userService.getById(id);
        if (user == null) {
            String errorMessage = "User with id " + id + " not found";
            System.out.println(errorMessage);
            messages.put("error id",errorMessage);
        }

        if (!dateValidator.isThisYearValid(year)){
            String errorMessage = "Not a valid year: '" + year + "'";
            System.out.println(errorMessage);
            messages.put("error year",errorMessage);
        }

        if (!dateValidator.isThisMonthValid(month)){
            String errorMessage = "Not a valid month: '" + month + "'";
            System.out.println(errorMessage);
            messages.put("error month",errorMessage);
        }

        if (!messages.isEmpty()){ return messages; }

        if (userSalaryService.findByUserIdAndYearAndMonth(id,Integer.parseInt(year),Integer.parseInt(month)) != null){
            String errorMessage = "Salary for the userId " + id + " for '" + year + "-" + month + "' already stored in DB.";
            System.out.println(errorMessage);
            messages.put("error salary",errorMessage);
        }

        return messages;
    }

    public Map<String, String> checkUserYearMonthInput(String year, String month){
        Map<String, String> messages = new HashMap<String, String>();

        if (!dateValidator.isThisYearValid(year)){
            String errorMessage = "Not a valid year: '" + year + "'";
            System.out.println(errorMessage);
            messages.put("error year",errorMessage);
        }

        if (!dateValidator.isThisMonthValid(month)){
            String errorMessage = "Not a valid month: '" + month + "'";
            System.out.println(errorMessage);
            messages.put("error month",errorMessage);
        }

        if (!messages.isEmpty()){ return messages; }

        List<Long> ids = userSalaryService.findByYearAndMonth(Integer.parseInt(year),Integer.parseInt(month));
        if (ids != null && !ids.isEmpty()){
            String errorMessage = "Salary for '" + year + "-" + month + "' already stored in DB.";
            System.out.println(errorMessage);
            messages.put("error salary",errorMessage);
        }

        return messages;
    }

    public Map<String, String> checkUserYearMonthInputSimple(long id, String year, String month){
        Map<String, String> messages = new HashMap<String, String>();

        User user = userService.getById(id);
        if (user == null) {
            String errorMessage = "User with id " + id + " not found";
            System.out.println(errorMessage);
            messages.put("error id",errorMessage);
        }

        if (!dateValidator.isThisYearValid(year)){
            String errorMessage = "Not a valid year: '" + year + "'";
            System.out.println(errorMessage);
            messages.put("error year",errorMessage);
        }

        if (!dateValidator.isThisMonthValid(month)){
            String errorMessage = "Not a valid month: '" + month + "'";
            System.out.println(errorMessage);
            messages.put("error month",errorMessage);
        }

        return messages;
    }

    public Map<String, String> checkUserYearMonthInputSimple(String year, String month){
        Map<String, String> messages = new HashMap<String, String>();

        if (!dateValidator.isThisYearValid(year)){
            String errorMessage = "Not a valid year: '" + year + "'";
            System.out.println(errorMessage);
            messages.put("error year",errorMessage);
        }

        if (!dateValidator.isThisMonthValid(month)){
            String errorMessage = "Not a valid month: '" + month + "'";
            System.out.println(errorMessage);
            messages.put("error month",errorMessage);
        }

        return messages;
    }

    public Map<String, String> ifYearMonthSalaryExists(Integer year, Integer month) {
        Map<String, String> messages = new HashMap<String, String>();
        List<Long> ids = userSalaryService.findByYearAndMonth(year,month);
        if (ids != null && !ids.isEmpty()){
            String errorMessage = "Salary for '" + year + "-" + month + "' already stored in DB.";
            System.out.println(errorMessage);
            messages.put("error salary",errorMessage);
        }
        return messages;
    }

}
