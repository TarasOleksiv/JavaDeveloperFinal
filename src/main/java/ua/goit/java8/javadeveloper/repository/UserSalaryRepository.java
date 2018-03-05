package ua.goit.java8.javadeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java8.javadeveloper.report.entity.UserSalaryAggregation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

public interface UserSalaryRepository extends JpaRepository<UserSalaryAggregation,Long> {

    //---------------- Calculate user salary for the period of time ----------------------------------------------------------------------------------------------

    @Query(value = "SELECT SUM(duration*salary_coef*hourly_rate) FROM user_salary where user_id = :id AND date between :startDate and :endDate", nativeQuery = true)
    BigDecimal getUserSalaryForPeriod(@Param("id") Long id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //---------------- Get user salary for the year-month ----------------------------------------------------------------------------------------------

    @Query(value = "SELECT salary FROM monthly_salary where user_id = :id AND year = :year AND month = :month", nativeQuery = true)
    BigDecimal getUserSalaryForYearMonth(@Param("id") Long id, @Param("year") Integer year, @Param("month") Integer month);


    //---------------- Store user monthly salary into the table --------------------------------------------------------------------------------------------------

    String query = "INSERT INTO monthly_salary (user_id,year,month,salary)" +
            " SELECT :user_id, :year, :month, " +
            " SUM(duration*salary_coef*hourly_rate)" +
            " FROM user_salary" +
            " WHERE user_id = :user_id AND date between :startDate and :endDate";

    @Modifying
    @Query(value = query, nativeQuery = true)
    @Transactional
    void storeUserMonthlySalary(
            @Param("user_id") Long id,
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);


    //----------------- Check if monthly salary is stored in the table for the user_id, year, month ----------------------------------------------------------------

    @Query(value = "SELECT ms.id FROM monthly_salary ms where ms.user_id = :id AND ms.year = :year AND ms.month = :month", nativeQuery = true)
    Long findByUserIdAndYearAndMonth(@Param("id") Long id, @Param("year") Integer year, @Param("month") Integer month);


    //----------------- Check if monthly salary is stored in the table for year, month ----------------------------------------------------------------

    @Query(value = "SELECT ms.id FROM monthly_salary ms where ms.year = :year AND ms.month = :month", nativeQuery = true)
    List<Long> findByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);


    //----------------- Delete monthly salary from the table for the user_id, year, month ----------------------------------------------------------------

    @Modifying
    @Transactional
    @Query(value = "DELETE ms.* FROM monthly_salary ms where ms.user_id = :id AND ms.year = :year AND ms.month = :month", nativeQuery = true)
    void deleteByUserIdAndYearAndMonth(@Param("id") Long id, @Param("year") Integer year, @Param("month") Integer month);


    //----------------- Delete monthly salary for all the users from the table for the year, month ----------------------------------------------------------------

    @Modifying
    @Transactional
    @Query(value = "DELETE ms.* FROM monthly_salary ms where ms.year = :year AND ms.month = :month", nativeQuery = true)
    void deleteByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);
}
