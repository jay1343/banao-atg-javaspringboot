psql -U postgres
CREATE DATABASE 'banao-atg';
\connect banao-atg;
CREATE SEQUENCE users_seq;
alter default privileges grant all on tables to postgres;
alter default privileges grant all on sequences to postgres;

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);
