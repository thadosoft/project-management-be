CREATE TABLE notifications
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_id CHAR(36)      NOT NULL,
    type         VARCHAR(50)   NOT NULL COMMENT 'LEAVE_SUBMITTED | LEAVE_APPROVED | LEAVE_REJECTED',
    title        VARCHAR(255)  NOT NULL,
    message      VARCHAR(1000) DEFAULT NULL,
    reference_id BIGINT        DEFAULT NULL COMMENT 'id of the related entity, e.g. leave_requests.id',
    is_read      BOOLEAN       NOT NULL DEFAULT FALSE,

    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_notifications_recipient FOREIGN KEY (recipient_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_notifications_recipient ON notifications (recipient_id, is_read);
