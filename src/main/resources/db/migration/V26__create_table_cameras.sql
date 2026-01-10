CREATE TABLE cameras (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         model_name VARCHAR(255) NOT NULL,
                         price DECIMAL(15, 2) NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);