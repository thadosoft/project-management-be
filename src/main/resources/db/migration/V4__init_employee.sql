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
    employee_code      VARCHAR(255) DEFAULT NULL,
    description        VARCHAR(255),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_staff_info ON employee (created_at, full_name, career);

INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (1, 'TDSHCM009', 'dungnt', NULL, 'Nguyễn Trung Dũng', 'Kỹ thuật', '2001-09-11', 'Việt Nam', 'Nam', '', 'dung.nguyentrung0911@gmail.com', '', 'dung.nt@gmail.com', '', '', '', '', '2025-03-11 08:58:18', '2025-03-11 09:00:08');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (2, 'TDSHCM002', 'haihv', NULL, 'Hồ Việt Hải', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'haihv@gmail.com', NULL, 'hai.hv@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 08:59:27', '2025-03-11 08:59:27');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (3, 'TDSHCM010', 'tienntu', NULL, 'Nguyễn Thị Út Tiên', 'Marketing', NULL, 'Việt Nam', 'Nữ', NULL, 'tienntu@gmail.com', NULL, 'tien.ntu@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:00:26', '2025-03-11 09:00:42');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (4, 'TDSHCM003', 'Giangvtt', NULL, 'Võ Thị Tham Giang', 'Kế toán', NULL, 'Việt Nam', 'Nữ', NULL, 'giangvtt@gmail.com', NULL, 'giang.vtt@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:01:15', '2025-03-11 09:01:15');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (5, 'TDSHCM008', 'khoand', NULL, 'Nguyễn Đình Khoa', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'khoand@gmail.com', NULL, 'khoa.nd@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:01:45', '2025-03-11 09:01:50');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (6, 'TDSHCM007', 'kientt', NULL, 'Trần Trung Kiên', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'kientt@gmail.com', NULL, 'kien.tt@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:02:32', '2025-03-11 09:02:32');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (7, 'TDSHCM006', 'trongtb', NULL, 'Trần Bình Trọng', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'trongtb@gmail.com', NULL, 'trong.tb@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:03:01', '2025-03-11 09:03:01');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (8, 'TDSHCM004', 'vuph', NULL, 'Phạm Hoàng Vũ', 'Sale', NULL, 'Việt Nam', 'Nam', NULL, 'vuph@gmail.com', NULL, 'vu.ph@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:03:38', '2025-03-11 09:03:40');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (9, 'TDSHCM005', 'khangnv', NULL, 'Nguyễn Vỉ Khang', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'khangnv@gmail.com', NULL, 'khang.nv@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:04:06', '2025-03-11 09:04:06');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (10, 'TDSHCM011', 'tunghp', NULL, 'Hoàng Phi Tùng', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'tunghp@gmail.com', NULL, 'tung.hp@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:04:06', '2025-03-11 09:04:06');
INSERT INTO employee (`id`, `employee_code`, `username`, `avatar`, `full_name`, `career`, `place_of_birth`, `nation`, `gender`, `tax`, `email`, `phone`, `company_email`, `emergency_contact`, `house_hold_address`, `current_address`, `description`, `created_at`, `updated_at`)
VALUES (11, 'TDSHCM001', 'khangnv', NULL, 'Nguyễn Hoàng Minh', 'Kỹ thuật', NULL, 'Việt Nam', 'Nam', NULL, 'minhnh@gmail.com', NULL, 'minh.nh@thadosoft.com', NULL, NULL, NULL, NULL, '2025-03-11 09:04:06', '2025-03-11 09:04:06');
