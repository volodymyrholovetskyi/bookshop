--liquibase formatted sql
--changeset vholovetskyi:4

CREATE SEQUENCE IF NOT EXISTS customer_id_seq
AS BIGINT
START WITH 1
INCREMENT BY 1;
