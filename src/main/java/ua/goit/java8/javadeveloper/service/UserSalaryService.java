package ua.goit.java8.javadeveloper.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by t.oleksiv on 01/03/2018.
 */
public interface UserSalaryService {

    BigDecimal getUserSalaryForPeriod(Long id, Date startDate, Date endDate);

    BigDecimal getUserSalaryForYearMonth(Long id, Integer year, Integer month);

    void storeUserMonthlySalary(Long id, Integer year, Integer month, Date startDate, Date endDate);

    Long findByUserIdAndYearAndMonth(Long id, Integer year, Integer month);

    List<Long> findByYearAndMonth(Integer year, Integer month);

    void deleteByUserIdAndYearAndMonth(Long id, Integer year, Integer month);

    void deleteByYearAndMonth(Integer year, Integer month);

}
