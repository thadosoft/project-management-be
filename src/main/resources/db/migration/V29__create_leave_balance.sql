CREATE TABLE leave_balance
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id  BIGINT NOT NULL,
    balance_year INT    NOT NULL,
    entitled     DOUBLE NOT NULL DEFAULT 12,
    used         DOUBLE NOT NULL DEFAULT 0,

    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_leave_balance_employee_year UNIQUE (employee_id, balance_year),
    CONSTRAINT fk_leave_balance_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);

-- Seed the current year with the default 12 days for every existing employee.
INSERT INTO leave_balance (employee_id, balance_year, entitled, used)
SELECT id, YEAR(CURDATE()), 12, 0
FROM employee;
