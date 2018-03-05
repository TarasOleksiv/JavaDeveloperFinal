package ua.goit.java8.javadeveloper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.java8.javadeveloper.repository.UserSalaryRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by t.oleksiv on 01/03/2018.
 */

@Service
public class UserSalaryServiceImpl implements UserSalaryService {

    @Autowired
    private UserSalaryRepository userSalaryRepository;


    @Override
    public BigDecimal getUserSalaryForPeriod(Long id, Date startDate, Date endDate) {
        return userSalaryRepository.getUserSalaryForPeriod(id, startDate, endDate);
    }

    @Override
    public BigDecimal getUserSalaryForYearMonth(Long id, Integer year, Integer month) {
        return userSalaryRepository.getUserSalaryForYearMonth(id, year, month);
    }

    @Override
    public void storeUserMonthlySalary(Long id, Integer year, Integer month, Date startDate, Date endDate) {
        userSalaryRepository.storeUserMonthlySalary(id, year, month, startDate, endDate);
    }

    @Override
    public Long findByUserIdAndYearAndMonth(Long id, Integer year, Integer month) {
        return userSalaryRepository.findByUserIdAndYearAndMonth(id, year, month);
    }

    @Override
    public List<Long> findByYearAndMonth(Integer year, Integer month) {
        return userSalaryRepository.findByYearAndMonth(year, month);
    }

    @Override
    public void deleteByUserIdAndYearAndMonth(Long id, Integer year, Integer month) {
        userSalaryRepository.deleteByUserIdAndYearAndMonth(id, year, month);
    }

    @Override
    public void deleteByYearAndMonth(Integer year, Integer month) {
        userSalaryRepository.deleteByYearAndMonth(year, month);
    }

}
