ALTER TABLE order_items
    ADD COLUMN quantity INT NOT NULL DEFAULT 1 AFTER priority,
    ADD COLUMN note VARCHAR(500) NULL AFTER quantity;