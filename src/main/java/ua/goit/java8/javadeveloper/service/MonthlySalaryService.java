package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.model.User;
import ua.goit.java8.javadeveloper.service.UserSalaryService;
import ua.goit.java8.javadeveloper.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * Created by t.oleksiv on 02/03/2018.
 */

@Service
public class MonthlySalaryService {

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleDateFormat dateFormatter;

    public void storeMonthlySalary(Integer year, Integer month){

        if ((month > 12)
                || (month <= 0)
                || (year <= 0)) { return; }

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

        List<User> users = userService.getAll();
        if ((users != null) && (!users.isEmpty())){
            for (User user: users){
                userSalaryService.storeUserMonthlySalary(user.getId(), year, month, startDate, endDate);
            }
        }

    }
}
