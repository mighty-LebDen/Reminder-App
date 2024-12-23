--liquibase formatted sql

--changeset dlebedev:1
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username varchar(128) NOT NULL UNIQUE,
    password varchar(128),
    telegram varchar(128)
);


--changeset dlebedev:2
CREATE TABLE IF NOT EXISTS reminder
(
    id          BIGSERIAL PRIMARY KEY,
    title       varchar(255)  NOT NULL,
    description varchar(4096) NOT NULL,
    remind      timestamp,
    user_id     bigint references users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    is_sent     varchar(10) not null default 'NOT_SENT'
);