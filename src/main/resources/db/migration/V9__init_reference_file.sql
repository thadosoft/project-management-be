CREATE TABLE reference_file_v2
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_name            VARCHAR(255) ,
    file_type            VARCHAR(100),
    file_size            BIGINT      ,
    file_path            VARCHAR(512) ,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);