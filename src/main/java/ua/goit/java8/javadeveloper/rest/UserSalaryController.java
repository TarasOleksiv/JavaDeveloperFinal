package ua.goit.java8.javadeveloper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.java8.javadeveloper.report.entity.UserMonthlySalaryAggregation;
import ua.goit.java8.javadeveloper.service.EmailService;
import ua.goit.java8.javadeveloper.service.MonthlySalaryService;
import ua.goit.java8.javadeveloper.report.entity.UserSalaryAggregation;
import ua.goit.java8.javadeveloper.service.UserSalaryService;
import ua.goit.java8.javadeveloper.service.UserService;
import ua.goit.java8.javadeveloper.validator.RoleUserValidator;
import ua.goit.java8.javadeveloper.validator.SalaryValidator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t.oleksiv on 01/03/2018.
 */

@RestController
@RequestMapping(value = "api/admin/salary")
public class UserSalaryController {

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private MonthlySalaryService monthlySalaryService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SimpleDateFormat dateFormatter;

    @Autowired
    private SalaryValidator salaryValidator;

    @Autowired
    private RoleUserValidator roleUserValidator;


    //--------------------- Retrieve User Salary for the Period (integrated security)------------------------------------------------------------------------

    @RequestMapping(value = {"/{id}|{startDate}|{endDate}"
            , "/{id}|{startDate}|{endDate}/hasuseraccess"}
            , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserSalary(
            @PathVariable("id") long id,
            @PathVariable("startDate") String startDateStr,
            @PathVariable("endDate") String endDateStr,
            Authentication authentication){

        System.out.println("Calculating Salary for userId " + id);

        Map<String, String> messages = salaryValidator.checkPeriodInput(id, startDateStr, endDateStr);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        if (!roleUserValidator.isAllowedToRead(id, authentication)){
            messages.put("error", "Access Denied");
            return new ResponseEntity<>(messages, HttpStatus.FORBIDDEN);
        }

        Date startDate = null;
        try {
            startDate = dateFormatter.parse(startDateStr);
            } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endDate = null;
        try {
            endDate = dateFormatter.parse(endDateStr);
           } catch (ParseException e) {
            e.printStackTrace();
        }

        String username = userService.getById(id).getUsername();
        BigDecimal salary = userSalaryService.getUserSalaryForPeriod(id, startDate, endDate);
        salary = (salary == null?BigDecimal.valueOf(0):salary);
        UserSalaryAggregation userSalaryAggregation = new UserSalaryAggregation();
        userSalaryAggregation.setUserId(id);
        userSalaryAggregation.setStartDate(startDate);
        userSalaryAggregation.setEndDate(endDate);
        userSalaryAggregation.setUsername(username);
        userSalaryAggregation.setSalary(salary);
        return new ResponseEntity<>(userSalaryAggregation, HttpStatus.OK);
    }

    //--------------------- Store Monthly User Salary ------------------------------------------------------------------------

    @RequestMapping(value = "/{id}|{year}|{month}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> setUserMonthlySalary(
            @PathVariable("id") long id,
            @PathVariable("year") String year,
            @PathVariable("month") String month){

        Map<String, String> messages = salaryValidator.checkUserYearMonthInput(id, year, month);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        String startDateStr = year + "-" + month + "-" + "01";

        Date startDate = null;
        try {
            startDate = dateFormatter.parse(startDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate startDateLocal = null;
        try {
            startDateLocal = dateFormatter.parse(startDateStr).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate endDateLocal = startDateLocal.with(TemporalAdjusters.lastDayOfMonth());
        Date endDate = Date.from(endDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        userSalaryService.storeUserMonthlySalary(id, Integer.parseInt(year), Integer.parseInt(month), startDate, endDate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //--------------------- Delete Stored Monthly User Salary for one user ------------------------------------------------------------------------

    @RequestMapping(value = "/{id}|{year}|{month}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUserMonthlySalary(
            @PathVariable("id") long id,
            @PathVariable("year") String year,
            @PathVariable("month") String month){

        Map<String, String> messages = salaryValidator.checkUserYearMonthInputSimple(id, year, month);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        userSalaryService.deleteByUserIdAndYearAndMonth(id, Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //--------------------- Delete Stored Monthly User Salary for all users ------------------------------------------------------------------------

    @RequestMapping(value = "/delete/{year}|{month}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteMonthlySalary(
            @PathVariable("year") String year,
            @PathVariable("month") String month){

        Map<String, String> messages = salaryValidator.checkUserYearMonthInputSimple(year, month);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        userSalaryService.deleteByYearAndMonth(Integer.parseInt(year), Integer.parseInt(month));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //--------------------- Store Monthly Salary for all users ------------------------------------------------------------------------

    @RequestMapping(value = "/store/{year}|{month}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> setMonthlySalary(
            @PathVariable("year") String year,
            @PathVariable("month") String month){

        Map<String, String> messages = salaryValidator.checkUserYearMonthInput(year, month);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        monthlySalaryService.storeMonthlySalary(Integer.parseInt(year), Integer.parseInt(month));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //--------------------- Send Stored Monthly User Salary to the user ------------------------------------------------------------------------

    @RequestMapping(value = "/sendemail/{id}|{year}|{month}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> sendUserMonthlySalary(
            @PathVariable("id") long id,
            @PathVariable("year") String year,
            @PathVariable("month") String month){

        Map<String, String> messages = new HashMap<>();
        messages = salaryValidator.checkUserYearMonthInputSimple(id, year, month);
        if (!messages.isEmpty()){
            System.out.println("Wrong Input!");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }

        String email = userService.getById(id).getEmail();
        if ((email == null) || email.isEmpty()){
            messages.put("email", "null or empty");
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        }

        BigDecimal salary = userSalaryService.getUserSalaryForYearMonth(id, Integer.parseInt(year), Integer.parseInt(month));
        salary = (salary == null?BigDecimal.valueOf(0):salary);
        String username = userService.getById(id).getUsername();

        UserMonthlySalaryAggregation userMonthlySalaryAggregation = new UserMonthlySalaryAggregation();
        userMonthlySalaryAggregation.setUserId(id);
        userMonthlySalaryAggregation.setYear(Integer.parseInt(year));
        userMonthlySalaryAggregation.setMonth(Integer.parseInt(month));
        userMonthlySalaryAggregation.setUsername(username);
        userMonthlySalaryAggregation.setSalary(salary);
        userMonthlySalaryAggregation.setEmail(email);

        String response = emailService.sendReportToUser(userMonthlySalaryAggregation);
        System.out.println(response);
        messages.put("server response", response);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
