CREATE TABLE book_loans
(
    loan_id        INT AUTO_INCREMENT PRIMARY KEY,
    book_title     VARCHAR(255) NULL,
    borrower_name  VARCHAR(255) NULL,
    borrow_date    DATETIME  DEFAULT CURRENT_TIMESTAMP,
    book_owner     VARCHAR(255) NULL,
    book_condition VARCHAR(100) NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
