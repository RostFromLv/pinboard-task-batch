create table if not exists users
(
    id               SERIAL PRIMARY KEY,
    name             TEXT        NOT NULL,
    last_name        TEXT        NOT NULL,
    age              INT,
    email            VARCHAR(63) NOT NULL,
    phone_number     VARCHAR(12)
);