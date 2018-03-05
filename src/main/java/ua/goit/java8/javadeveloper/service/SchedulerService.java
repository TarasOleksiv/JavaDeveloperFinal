package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.report.entity.UserMonthlySalaryAggregation;
import ua.goit.java8.javadeveloper.validator.SalaryValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * Created by t.oleksiv on 04/03/2018.
 */

@Service
public class SchedulerService {

    @Autowired
    private MonthlySalaryService monthlySalaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SalaryValidator salaryValidator;

    //@Scheduled(fixedRate = 20000, initialDelay = 20000)
    //cron = "second, minute, hour, day, month, weekday"
    @Scheduled(cron = "0 0 2 1 * ?")
    public void storeMonthlySalary() {

        LocalDate localDate = LocalDate.now();
        LocalDate previousMonth = localDate.plus(-1, ChronoUnit.MONTHS);
        Integer year = previousMonth.getYear();
        Integer month = previousMonth.getMonthValue();

        Map<String, String> messages = salaryValidator.ifYearMonthSalaryExists(year, month);
        if (!messages.isEmpty()){
            System.out.println(messages.get("error salary"));
            return;
        }


        //-------------- Store into the table monthly salary for all users for the previous month ----------------------------
        monthlySalaryService.storeMonthlySalary(year, month);
        System.out.println("Salary for " + year + "-" + month + " stored into DB successfully!");


        //-------------- Send monthly salary report to each user -------------------------------------------------------------
        List<User> users = userService.getAll();

        UserMonthlySalaryAggregation userMonthlySalaryAggregation = new UserMonthlySalaryAggregation();
        String username;
        String email;
        BigDecimal salary;
        String response;

        for (User user: users){
            email = user.getEmail();
            if ((email == null) || email.isEmpty()){
                messages.put("userId " + user.getId(), "userId: " + user.getId() + " - mail null or empty");
                System.out.println(messages.get("userId " + user.getId()));
                continue;
            }
            salary = userSalaryService.getUserSalaryForYearMonth(user.getId(), year, month);
            salary = (salary == null?BigDecimal.valueOf(0):salary);
            username = user.getUsername();

            userMonthlySalaryAggregation.setUserId(user.getId());
            userMonthlySalaryAggregation.setYear(year);
            userMonthlySalaryAggregation.setMonth(month);
            userMonthlySalaryAggregation.setUsername(username);
            userMonthlySalaryAggregation.setSalary(salary);
            userMonthlySalaryAggregation.setEmail(email);

            response = emailService.sendReportToUser(userMonthlySalaryAggregation);
            System.out.println(response);
            messages.put("server response", response);
            System.out.println(messages.get("server response"));
        }

    }
}
