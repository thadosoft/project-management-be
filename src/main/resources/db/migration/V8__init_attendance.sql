alter table employee
    add COLUMN total_leave DOUBLE;

CREATE TABLE shift
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    start_time TIME         NOT NULL,
    end_time   TIME         NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE attendance
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id    BIGINT NOT NULL,
    work_date      DATE   NOT NULL,
    shift_id       BIGINT NOT NULL,
    status         VARCHAR(50) NULL,
    check_in_time  TIME     DEFAULT NULL,
    check_out_time TIME     DEFAULT NULL,

    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_employee_attendance FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    CONSTRAINT fk_shift_attendance FOREIGN KEY (shift_id) REFERENCES shift (id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (employee_id, work_date, shift_id)
);


CREATE TABLE leave_requests
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    start_date  DATE   NOT NULL,
    end_date    DATE   NOT NULL,
    leave_type  VARCHAR(50),
    reason      TEXT     DEFAULT NULL,
    status      VARCHAR(50),

    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);
