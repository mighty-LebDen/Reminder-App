--liquibase formatted sql

--changelog dlebedev:1
ALTER TABLE IF EXISTS users
    ADD COLUMN chat_id BIGINT UNIQUE;

--changelog dlebedev:2
ALTER TABLE IF EXISTS reminder
    ADD COLUMN is_sent_telegram VARCHAR(10) NOT NULL DEFAULT 'NOT_SENT';