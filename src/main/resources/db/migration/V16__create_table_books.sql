CREATE TABLE books (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL UNIQUE,
                       author VARCHAR(255),
                       category VARCHAR(100),
                       publisher VARCHAR(255),
                       publication_year INT,
                       quantity_total INT,
                       quantity_available INT,
                       available BOOLEAN DEFAULT TRUE,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       created_by VARCHAR(255),
                       updated_by VARCHAR(255)
);
