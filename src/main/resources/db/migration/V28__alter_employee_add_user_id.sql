ALTER TABLE employee
    ADD COLUMN user_id CHAR(36) DEFAULT NULL AFTER id;

-- Backfill the link for existing rows by matching the loose username column.
-- Only rows whose username is unique within the employee table are linked, so
-- the unique index below cannot collide on duplicated legacy usernames.
UPDATE employee e
    JOIN users u ON e.username = u.username
SET e.user_id = u.id
WHERE e.user_id IS NULL
  AND e.username IN (SELECT username
                     FROM (SELECT username FROM employee GROUP BY username HAVING COUNT(*) = 1) dedup);

ALTER TABLE employee
    ADD CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES users (id);

CREATE UNIQUE INDEX uk_employee_user_id ON employee (user_id);
