CREATE TABLE employee_of_month
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    month_year  DATE   NOT NULL,
    reason      TEXT,
    month       INTEGER  DEFAULT NULL,
    year        INTEGER  DEFAULT NULL,
    award_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_employee_of_month_employee
        FOREIGN KEY (employee_id)
            REFERENCES employee (id)
            ON DELETE CASCADE
);