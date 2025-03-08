CREATE TABLE quotation_request
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(255) NOT NULL,

    requester_name    VARCHAR(255) NOT NULL,
    requester_email   VARCHAR(255) NOT NULL,
    requester_tel     VARCHAR(50)  NOT NULL,
    requester_address VARCHAR(255) NOT NULL,
    requester_website VARCHAR(255) NULL,

    receiver_name     VARCHAR(255) NOT NULL,
    receiver_email    VARCHAR(255) NOT NULL,
    receiver_tel      VARCHAR(50)  NOT NULL,
    receiver_address  VARCHAR(255) NOT NULL,
    receiver_website  VARCHAR(255) NULL,

    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_quotation_request ON quotation_request (created_at, title, requester_name, receiver_name);

CREATE TABLE material_quotation
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    quotation_request_id BIGINT NOT NULL,
    code                 VARCHAR(255) NULL,
    description          VARCHAR(255) NULL,
    unit                 VARCHAR(255) NULL,
    quantity             VARCHAR(255) NULL,
    delivery_date        VARCHAR(255) NULL,
    price                VARCHAR(255) NULL,
    tax                  VARCHAR(255) NULL,
    price_no_tax         VARCHAR(255) NULL,
    price_tax            VARCHAR(255) NULL,
    total_price          VARCHAR(255) NULL,

    created_at           DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_quotation_request FOREIGN KEY (quotation_request_id)
        REFERENCES quotation_request (id) ON DELETE CASCADE
);
