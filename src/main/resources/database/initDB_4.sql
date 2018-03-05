# 13. Create table to store user monthly salary.
CREATE TABLE monthly_salary (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_id BIGINT(20) NOT NULL,
  year INT NOT NULL,
  month INT NOT NULL,
  salary DECIMAL(7,2) NULL DEFAULT 0.00,
  PRIMARY KEY (id),
  UNIQUE INDEX user_year_month (user_id ASC, year ASC, month ASC),
  CONSTRAINT user_salary_fk
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;