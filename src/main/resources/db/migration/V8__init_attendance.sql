alter table employee
    add COLUMN total_leave DOUBLE;

CREATE TABLE period
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,

    month      INTEGER NULL DEFAULT NULL,
    year       INTEGER NULL DEFAULT NULL,
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS attendance
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_code      VARCHAR(50)  NOT NULL,
    full_name          VARCHAR(255) NOT NULL,
    work_date          DATE         NOT NULL,
    shift_name         VARCHAR(50)  NULL,
    total_shifts       DECIMAL(3, 1)     DEFAULT 0.0,
    morning_checkin    VARCHAR(255)      DEFAULT NULL,
    afternoon_checkout VARCHAR(255)      DEFAULT NULL,
    other_checkins     VARCHAR(255)      DEFAULT NULL,
    period_id          BIGINT       NULL DEFAULT NULL,
    created_at         DATETIME          DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (period_id) REFERENCES period (id) ON DELETE CASCADE
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
