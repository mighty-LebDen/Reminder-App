--liquibase formatted sql

--changeset dlebedev:3
ALTER TABLE IF EXISTS users
    ADD COLUMN chat_id BIGINT UNIQUE;

--changeset dlebedev:4
ALTER TABLE IF EXISTS reminder
    ADD COLUMN is_sent_telegram VARCHAR(10) NOT NULL DEFAULT 'NOT_SENT';