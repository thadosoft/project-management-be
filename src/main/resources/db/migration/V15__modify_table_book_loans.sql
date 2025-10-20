ALTER TABLE book_loans
    MODIFY COLUMN loan_id BIGINT AUTO_INCREMENT,
    ADD COLUMN borrower_id BIGINT NULL AFTER borrower_name,
    ADD COLUMN due_date DATETIME NULL AFTER borrow_date,
    ADD COLUMN status VARCHAR (50) DEFAULT 'PENDING' AFTER due_date,
    ADD COLUMN approver_name VARCHAR (255) NULL AFTER status,
    ADD COLUMN approved_at DATETIME NULL AFTER approver_name,
    ADD COLUMN returned_at DATETIME NULL AFTER approved_at,
    ADD COLUMN is_available BOOLEAN DEFAULT TRUE AFTER book_condition,
    ADD COLUMN remarks VARCHAR (500) NULL AFTER returned_at;

UPDATE book_loans
SET is_available = TRUE
WHERE is_available IS NULL;