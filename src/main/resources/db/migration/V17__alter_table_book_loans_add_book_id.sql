-- Kiểm tra nếu chưa có cột book_id thì mới thêm
-- MySQL không cho IF NOT EXISTS trực tiếp trong ALTER TABLE, nên ta dùng thủ thuật INFORMATION_SCHEMA

SET @column_exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'book_loans'
      AND COLUMN_NAME = 'book_id'
      AND TABLE_SCHEMA = DATABASE()
);

-- Nếu cột chưa tồn tại thì thêm
SET @sql := IF(@column_exists = 0,
    'ALTER TABLE book_loans ADD COLUMN book_id BIGINT;',
    'SELECT "Column book_id already exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Thêm ràng buộc khóa ngoại (nếu chưa có)
SET @constraint_exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_book_loans_books'
      AND TABLE_NAME = 'book_loans'
      AND TABLE_SCHEMA = DATABASE()
);

SET @sql2 := IF(@constraint_exists = 0,
    'ALTER TABLE book_loans
        ADD CONSTRAINT fk_book_loans_books
        FOREIGN KEY (book_id) REFERENCES books(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE;',
    'SELECT "Constraint fk_book_loans_books already exists";'
);
PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;
