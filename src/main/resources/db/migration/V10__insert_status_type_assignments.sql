UPDATE assignments SET status_type = 'IN_PROGRESS' WHERE status_type IS NULL;

ALTER TABLE assignments ADD COLUMN start_date DATETIME;
ALTER TABLE assignments ADD COLUMN end_date DATETIME;
