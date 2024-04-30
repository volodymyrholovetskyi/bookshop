--liquibase formatted sql
--changeset vholovetskyi:7

CREATE SEQUENCE IF NOT EXISTS order_id_seq
AS BIGINT
START WITH 1
INCREMENT BY 1;
