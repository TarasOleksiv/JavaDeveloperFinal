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