# 04. Populate positions;
INSERT INTO positions (name) VALUES
  ('MANAGER'),
  ('DRIVER'),
  ('IT SPECIALIST');
# 05. Populate departments;
INSERT INTO departments (name) VALUES
  ('IT'),
  ('ACCOUNTING'),
  ('TRANSPORT');
# 06. Populate event_types;
INSERT INTO event_types (name, salary_coef) VALUES
  ('working day', 1),
  ('business trip', 1.2),
  ('vacation', 1),
  ('sick-leave', 0.8);
# 07. Populate status_types;
INSERT INTO status_types (name) VALUES
  ('work'),
  ('vacation'),
  ('sick-leave');
# 08. Populate user positions;
SELECT @posManid:=id FROM positions WHERE name = 'MANAGER';
SELECT @posDriverid:=id FROM positions WHERE name = 'DRIVER';
SELECT @posITid:=id FROM positions WHERE name = 'IT SPECIALIST';
SELECT @userid:=id FROM users WHERE username = 'admin';
INSERT INTO user_positions (user_id, position_id) VALUES
  (@userid, @posITid);
SELECT @userid:=id FROM users WHERE username = 'guest';
INSERT INTO user_positions (user_id, position_id) VALUES
  (@userid, @posDriverid);
SELECT @userid:=id FROM users WHERE username = 'toleksiv';
INSERT INTO user_positions (user_id, position_id) VALUES
  (@userid, @posManid);
# 09. Populate user departments;
SELECT @depITid:=id FROM departments WHERE name = 'IT';
SELECT @depAccid:=id FROM departments WHERE name = 'ACCOUNTING';
SELECT @depTransid:=id FROM departments WHERE name = 'TRANSPORT';
SELECT @userid:=id FROM users WHERE username = 'admin';
INSERT INTO user_departments (user_id, department_id) VALUES
  (@userid, @depITid);
SELECT @userid:=id FROM users WHERE username = 'guest';
INSERT INTO user_departments (user_id, department_id) VALUES
  (@userid, @depTransid);
SELECT @userid:=id FROM users WHERE username = 'toleksiv';
INSERT INTO user_departments (user_id, department_id) VALUES
  (@userid, @depAccid);
