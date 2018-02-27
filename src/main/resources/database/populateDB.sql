# 01. Populate roles;
INSERT INTO roles (name) VALUES
  ('ROLE_ADMIN'),
  ('ROLE_MODERATOR'),
  ('ROLE_USER');
# 02. Populate users;
INSERT INTO users (username, firstname, lastname, password, email, hourly_rate) VALUES
  ('admin', 'admin', 'admin', '$2a$10$MBNLCmt0VanScq0U.glhwulABB06a5z8RIBFOsL4xhoxegaSdNuH6','admin@example.com', 10),
  ('toleksiv', 'taras', 'oleksiv', '$2a$10$d1dnh071WHfnTzSaHL.T3.loKhECNnmEqwgPMYhTgpwuEbvMSngpu','toleksiv@example.com', 15),
  ('guest', 'guest', 'guest', '$2a$10$ODDivyvkir2Oh1RHc5ajb.5Ftv7Q2wUUoqhyojSyxvny8rmPYt03a','guest@example.com', 20);
# 03. Populate user roles;
SELECT @roleAdminid:=id FROM roles WHERE name = 'ROLE_ADMIN';
SELECT @roleModeratorid:=id FROM roles WHERE name = 'ROLE_MODERATOR';
SELECT @roleUserid:=id FROM roles WHERE name = 'ROLE_USER';
SELECT @userid:=id FROM users WHERE username = 'admin';
INSERT INTO user_roles (user_id, role_id) VALUES
  (@userid, @roleAdminid);
SELECT @userid:=id FROM users WHERE username = 'guest';
INSERT INTO user_roles (user_id, role_id) VALUES
  (@userid, @roleUserid);
SELECT @userid:=id FROM users WHERE username = 'toleksiv';
INSERT INTO user_roles (user_id, role_id) VALUES
  (@userid, @roleModeratorid);
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
# 10. Populate events;
SELECT @WDid:=id FROM event_types WHERE name = 'working day';
SELECT @SLid:=id FROM event_types WHERE name = 'sick-leave';
INSERT INTO events (date, event_type, duration) VALUES
  ('2018-02-24', @WDid, 8),
  ('2018-02-25', @WDid, 8),
  ('2018-02-25', @SLid, 8);
# 11. Populate event_users;
INSERT INTO event_users (event_id, user_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 1),
  (2, 2),
  (3, 3);