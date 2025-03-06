CREATE TABLE employee
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    username           VARCHAR(50),
    avatar             LONGTEXT,
    full_name          varchar(255),
    career             VARCHAR(50),
    place_of_birth     VARCHAR(50),
    nation             VARCHAR(50),
    gender             VARCHAR(50),
    tax                VARCHAR(50),
    email              VARCHAR(50),
    phone              VARCHAR(50),
    company_email      VARCHAR(50),
    emergency_contact  VARCHAR(50),
    house_hold_address VARCHAR(255),
    current_address    VARCHAR(255),
    description        VARCHAR(255),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_staff_info ON employee (created_at, full_name, career);
