package ua.goit.java8.javadeveloper.service;

/**
 * Created by t.oleksiv on 04/03/2018.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.report.entity.UserMonthlySalaryAggregation;

import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    public String sendReportToUser(UserMonthlySalaryAggregation userMonthlySalaryAggregation){
        try {
            sendEmail(userMonthlySalaryAggregation);
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: " + ex;
        }
    }

    private void sendEmail(UserMonthlySalaryAggregation userMonthlySalaryAggregation) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Integer year = userMonthlySalaryAggregation.getYear();
        Integer month = userMonthlySalaryAggregation.getMonth();
        BigDecimal salary = userMonthlySalaryAggregation.getSalary();
        String messageBody = "Hello " + userMonthlySalaryAggregation.getUsername() + ",\n";
        messageBody += "Your salary for " + year + "-" + month + " : " + salary;
        helper.setTo(userMonthlySalaryAggregation.getEmail());
        helper.setText(messageBody);
        helper.setSubject("Monthly salary for " + year + "-" + month);

        sender.send(message);
    }
}