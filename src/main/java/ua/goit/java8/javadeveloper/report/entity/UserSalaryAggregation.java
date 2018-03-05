package ua.goit.java8.javadeveloper.report.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.goit.java8.javadeveloper.json.CustomUserSalarySerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by t.oleksiv on 01/03/2018.
 */

@JsonSerialize(using = CustomUserSalarySerializer.class)
@Entity
@IdClass(UserSalaryAggregation.class)
public class UserSalaryAggregation implements Serializable{

    //This resembles the business key:
    @Id
    private Long userId;

    @Id
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Id
    @Temporal(TemporalType.DATE)
    private Date endDate;
    //KEY - END

    private String username;
    private BigDecimal salary;

    public UserSalaryAggregation() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        UserSalaryAggregation obj2 = (UserSalaryAggregation) obj;
        if((this.userId == obj2.getUserId())
                && (this.startDate.equals(obj2.getStartDate()))
                && (this.endDate.equals(obj2.getEndDate()))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int tmp = 0;
        tmp = ( userId + startDate.toString() + endDate.toString()).hashCode();
        return tmp;
    }
}
