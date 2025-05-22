ALTER TABLE reference_file_v2
    ADD COLUMN inventory_item_id BIGINT NOT NULL;

ALTER TABLE reference_file_v2
    ADD COLUMN access_url varchar(255) NULL;


ALTER TABLE reference_file_v2
    ADD CONSTRAINT fk_reference_file_v2_inventory_item
        FOREIGN KEY (inventory_item_id)
            REFERENCES inventory_item (id)
            ON DELETE CASCADE;