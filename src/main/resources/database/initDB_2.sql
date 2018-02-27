# 10. Create table events;
CREATE TABLE events (
  id BIGINT AUTO_INCREMENT NOT NULL,
  date DATE NOT NULL,
  event_type BIGINT NOT NULL,
  duration INT NOT NULL DEFAULT 8,
  PRIMARY KEY (id),
  FOREIGN KEY (event_type) REFERENCES event_types (id) ON DELETE CASCADE,
  UNIQUE INDEX EVENT_DATE_IDX (date, event_type))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 11. Create table event_users;
CREATE TABLE event_users (
  #id BIGINT AUTO_INCREMENT NOT NULL,
  event_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  #PRIMARY KEY (id),
  FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  UNIQUE INDEX EVENT_USER_IDX (event_id, user_id))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
