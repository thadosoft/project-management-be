ALTER TABLE assignments ADD COLUMN status_type VARCHAR(100) DEFAULT 'IN_PROGRESS';
UPDATE assignments SET status_type = 'IN_PROGRESS' WHERE status_type IS NULL;
