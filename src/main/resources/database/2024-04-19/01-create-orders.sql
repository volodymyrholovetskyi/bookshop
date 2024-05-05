--liquibase formatted sql
--changeset vholovetskyi:4

CREATE TABLE IF NOT EXISTS orders
(
    order_id    SERIAL        NOT NULL,
    status      VARCHAR(70) NOT NULL,
    cust_id     INT8        NOT NULL,
    order_date  DATE DEFAULT NOW(),
    PRIMARY KEY (order_id),
    CONSTRAINT fk_customer_order_id FOREIGN KEY (cust_id)
    REFERENCES customer (cust_id)
);

CREATE TABLE IF NOT EXISTS order_item
(
    item     VARCHAR(255) NOT NULL,
    order_id INT8         NOT NULL,
    CONSTRAINT fk_order_item_id FOREIGN KEY (order_id)
    REFERENCES orders (order_id)
);
