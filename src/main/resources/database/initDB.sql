# 01. Create table roles;
CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX ROLE_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 02. Create table users;
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT NOT NULL,
  username VARCHAR(45) NOT NULL,
  firstname VARCHAR(45) NULL,
  lastname VARCHAR(45) NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NULL,
  hourly_rate DECIMAL(7,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE INDEX USERNAME_IDX (username ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 03. Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),
  UNIQUE (user_id, role_id))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 04. Create table positions;
CREATE TABLE positions (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX POSITION_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 05. Create table departments;
CREATE TABLE departments (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX DEPARTMENT_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 06. Create table event_types;
CREATE TABLE event_types (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX EVENT_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 07. Create table status_types;
CREATE TABLE status_types (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX STATUS_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 08. Table for mapping user and position: user_positions
CREATE TABLE user_positions (
  user_id BIGINT NOT NULL,
  position_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE CASCADE,
  UNIQUE (user_id))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 09. Table for mapping user and department: user_departments
CREATE TABLE user_departments (
  user_id BIGINT NOT NULL,
  department_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (department_id) REFERENCES departments (id)  ON DELETE CASCADE,
  UNIQUE (user_id))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;