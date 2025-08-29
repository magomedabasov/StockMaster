CREATE SCHEMA IF NOT EXISTS user_management;

CREATE TABLE user_management.t_user
(
    id         serial PRIMARY KEY,
    c_username varchar NOT NULL CHECK ( LENGTH(TRIM(c_username)) > 0) UNIQUE,
    c_password varchar NOT NULL CHECK ( LENGTH(TRIM(c_username)) > 0)
);

CREATE TABLE user_management.t_authority
(
    id          serial PRIMARY KEY,
    c_authority varchar NOT NULL CHECK ( LENGTH(TRIM(c_authority)) > 0) UNIQUE
);

CREATE TABLE user_management.t_user_authority
(
    id           serial PRIMARY KEY,
    id_user      int NOT NULL REFERENCES user_management.t_user (id),
    id_authority int NOT NULL REFERENCES user_management.t_authority (id),
    CONSTRAINT uk_user_authority UNIQUE (id_user, id_authority)
);