package ua.goit.java8.javadeveloper.report.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by t.oleksiv on 04/03/2018.
 */

@Entity
@IdClass(UserMonthlySalaryAggregation.class)
public class UserMonthlySalaryAggregation implements Serializable {

    //This resembles the business key:
    @Id
    private Long userId;

    @Id
    private Integer year;

    @Id
    private Integer month;
    //KEY - END

    private String username;
    private BigDecimal salary;
    private String email;

    public UserMonthlySalaryAggregation() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        UserMonthlySalaryAggregation obj2 = (UserMonthlySalaryAggregation) obj;
        if((this.userId == obj2.getUserId())
                && (this.year.equals(obj2.getYear()))
                && (this.month.equals(obj2.getMonth()))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int tmp = 0;
        tmp = ( userId + year.toString() + month.toString()).hashCode();
        return tmp;
    }
}
