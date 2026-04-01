
CREATE TABLE menu_items (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name        VARCHAR(255)   NOT NULL,
                            price       DECIMAL(10, 2) NOT NULL,
                            category    VARCHAR(100)   NOT NULL COMMENT 'e.g. Com, Bun, Pho, Mon them...',
                            image_url   VARCHAR(500)   NULL,
                            is_available BOOLEAN       NOT NULL DEFAULT TRUE,
                            created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id     CHAR(36)    NOT NULL,
                        order_date  DATE           NOT NULL,
                        status      VARCHAR(20)    NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING | CONFIRMED | CANCELLED',
                        created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        CONSTRAINT uk_orders_user_date UNIQUE (user_id, order_date),
                        CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
                             id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id      BIGINT   NOT NULL,
                             menu_item_id  BIGINT   NOT NULL,
                             priority      TINYINT  NOT NULL COMMENT '1 = most wanted, 5 = least wanted',

                             CONSTRAINT uk_order_items_priority UNIQUE (order_id, priority),
                             CONSTRAINT uk_order_items_menu_item UNIQUE (order_id, menu_item_id),
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             CONSTRAINT fk_order_items_menu FOREIGN KEY (menu_item_id) REFERENCES menu_items(id),
                             CONSTRAINT chk_priority_range CHECK (priority BETWEEN 1 AND 5)
);

CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_order_items_menu ON order_items(menu_item_id);