-- liquibase formatted sql

-- changeset koledov:1
CREATE TABLE animals
(
    id   SERIAL PRIMARY KEY,
    animal_type SMALLINT,
    animal_name TEXT
);

-- changeset koledov:3
CREATE TABLE shelters
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    address TEXT,
    opening_hours TEXT
);

-- changeset koledov:2
CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    user_name   TEXT,
    chat_id     BIGINT,
    is_volunteer BOOLEAN
);