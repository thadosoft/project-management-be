CREATE TABLE white_board
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id  BIGINT       NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    description  TEXT,
    deadline     DATE,
    start_date   DATE,
    end_date     DATE,
    status       VARCHAR(100),
    assign_by    VARCHAR(100),

    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_whiteboard_employee
        FOREIGN KEY (employee_id)
            REFERENCES employee (id)
            ON DELETE CASCADE
);
