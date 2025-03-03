CREATE TABLE inventory_category
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)       NOT NULL,
    code        VARCHAR(50) UNIQUE NOT NULL,
    description TEXT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE inventory_item
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                  VARCHAR(255)        NOT NULL,
    sku                   VARCHAR(255) UNIQUE NOT NULL,
    inventory_category_id BIGINT,
    unit                  VARCHAR(50)         NOT NULL,
    quantity_in_stock     INT       DEFAULT 0,
    reorder_level         INT       DEFAULT 0, -- mức tồn kho tối thiểu khi đạt đến => bổ sung tránh thiếu vật tư
    location              VARCHAR(255) NULL,
    purchase_price        DECIMAL(10, 2) NULL,
    selling_price         DECIMAL(10, 2) NULL,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_category_id) REFERENCES inventory_category (id)
);


CREATE TABLE inventory_transaction
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id          BIGINT       NOT NULL,
    transaction_type VARCHAR(50)  NOT NULL,
    quantity         INT          NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason           TEXT NULL,
    processed_by     VARCHAR(255) NOT NULL,
    receiver         VARCHAR(255) NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES inventory_item (id)
);
