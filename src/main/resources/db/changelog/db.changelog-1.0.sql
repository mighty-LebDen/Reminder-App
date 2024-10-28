--liquibase formatted sql

--changelog dlebedev:1
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username varchar(128) NOT NULL UNIQUE,
    password varchar(128) NOT NULL
);


--changelog dlebedev:1
CREATE TABLE IF NOT EXISTS remainders
(
    id          BIGSERIAL PRIMARY KEY,
    title       varchar(255)  NOT NULL,
    description varchar(4096) NOT NULL,
    remind      date,
    user_id     bigint references users (id) ON DELETE CASCADE ON UPDATE CASCADE
);