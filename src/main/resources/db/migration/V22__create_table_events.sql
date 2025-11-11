CREATE TABLE events (
                        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(225) NOT NULL UNIQUE,
                        description TEXT NULL,
                        location VARCHAR(225) NULL,
                        start_date DATETIME NOT NULL,
                        end_date DATETIME NULL,
                        type VARCHAR(50) NOT NULL DEFAULT 'OTHER',
                        project_id CHAR(36) NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        CONSTRAINT fk_events_projects
                            FOREIGN KEY (project_id)
                                REFERENCES projects(id)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE
)