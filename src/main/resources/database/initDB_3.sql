# 12. Create view to calculate user salary.
CREATE OR REPLACE VIEW user_salary AS
  SELECT e.id event_id, e.date, e.duration, et.salary_coef, u.id user_id, u.username, u.hourly_rate FROM events e
    LEFT JOIN event_types et on e.event_type = et.id
    LEFT JOIN event_users eu on e.id = eu.event_id
    LEFT JOIN users u on eu.user_id = u.id;
