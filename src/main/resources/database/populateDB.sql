# 01. Populate roles;
INSERT INTO roles (name) VALUES
  ('ROLE_ADMIN'),
  ('ROLE_MODERATOR'),
  ('ROLE_USER');
# 02. Populate users;
INSERT INTO users (username, firstname, lastname, password, email) VALUES
  ('admin', 'admin', 'admin', '$2a$10$MBNLCmt0VanScq0U.glhwulABB06a5z8RIBFOsL4xhoxegaSdNuH6','admin@example.com'),
  ('toleksiv', 'taras', 'oleksiv', '$2a$10$d1dnh071WHfnTzSaHL.T3.loKhECNnmEqwgPMYhTgpwuEbvMSngpu','toleksiv@example.com'),
  ('guest', 'guest', 'guest', '$2a$10$ODDivyvkir2Oh1RHc5ajb.5Ftv7Q2wUUoqhyojSyxvny8rmPYt03a','guest@example.com');
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