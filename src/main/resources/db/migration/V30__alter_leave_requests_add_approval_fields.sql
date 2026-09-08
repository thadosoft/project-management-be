ALTER TABLE leave_requests
    ADD COLUMN day_portion    DOUBLE       NOT NULL DEFAULT 1 AFTER end_date,
    ADD COLUMN number_of_days DOUBLE       NOT NULL DEFAULT 0 AFTER day_portion,
    ADD COLUMN approver_id    CHAR(36)     DEFAULT NULL AFTER status,
    ADD COLUMN approved_at    DATETIME     DEFAULT NULL AFTER approver_id,
    ADD COLUMN decision_note  VARCHAR(500) DEFAULT NULL AFTER approved_at;

-- Normalise any legacy rows before tightening the column.
UPDATE leave_requests SET status = 'PENDING' WHERE status IS NULL OR status = '';
UPDATE leave_requests
SET number_of_days = DATEDIFF(end_date, start_date) + 1
WHERE number_of_days = 0;

ALTER TABLE leave_requests
    MODIFY COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING';

ALTER TABLE leave_requests
    ADD CONSTRAINT fk_leave_requests_approver FOREIGN KEY (approver_id) REFERENCES users (id);

CREATE INDEX idx_leave_requests_employee ON leave_requests (employee_id);
CREATE INDEX idx_leave_requests_status ON leave_requests (status);
