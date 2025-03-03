CREATE TABLE module
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE reference_profile
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    module_id   BIGINT       NOT NULL,
    description LONGTEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (module_id) REFERENCES module (id) ON DELETE CASCADE
);

CREATE TABLE reference_files
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_profile_id BIGINT NOT NULL,
    file_data            LONGBLOB,
    file_name            VARCHAR(255),
    file_type            VARCHAR(100),
    file_size            BIGINT,
    file_url             LONGTEXT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reference_profile_id) REFERENCES reference_profile (id) ON DELETE CASCADE
);

CREATE TABLE reference_links
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_profile_id BIGINT NOT NULL,
    link                 LONGTEXT NULL,
    description          VARCHAR(500),
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reference_profile_id) REFERENCES reference_profile (id) ON DELETE CASCADE
);

CREATE INDEX idx_reference_profile_name ON reference_profile (name);
CREATE INDEX idx_reference_profile_createdAt ON reference_profile (created_at);
