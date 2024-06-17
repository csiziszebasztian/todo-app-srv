-- liquibase formatted sql

-- changeset dev:1
CREATE TABLE IF NOT EXISTS roles
(
    id          UUID        DEFAULT GEN_RANDOM_UUID() PRIMARY KEY,
    name        VARCHAR(255)              NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    modified_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- changeset dev:2
CREATE TABLE IF NOT EXISTS tokens
(
    id           UUID        DEFAULT GEN_RANDOM_UUID() PRIMARY KEY,
    token        VARCHAR(255),
    created_at   TIMESTAMPTZ DEFAULT NOW()                           NOT NULL,
    validated_at TIMESTAMPTZ,
    expired_at   TIMESTAMPTZ DEFAULT (NOW() + INTERVAL '15 minutes') NOT NULL,
    user_id      UUID                                                NOT NULL
);

-- changeset dev:3
CREATE TABLE IF NOT EXISTS users
(
    id             UUID        DEFAULT GEN_RANDOM_UUID() PRIMARY KEY,
    name           VARCHAR(255)              NOT NULL,
    email          VARCHAR(255) UNIQUE       NOT NULL,
    password       VARCHAR(255)              NOT NULL,
    account_locked BOOLEAN     DEFAULT FALSE NOT NULL,
    enabled        BOOLEAN     DEFAULT FALSE NOT NULL,
    created_at     TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    modified_at    TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- changeset dev:4
CREATE TABLE IF NOT EXISTS users_roles
(
    id         UUID        DEFAULT GEN_RANDOM_UUID() PRIMARY KEY,
    user_id    UUID                      NOT NULL,
    role_id    UUID                      NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- changeset dev:5
ALTER TABLE tokens
    ADD CONSTRAINT FK_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset dev:6
ALTER TABLE users_roles
    ADD CONSTRAINT FK_USERS_ROLES_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset dev:7
ALTER TABLE users_roles
    ADD CONSTRAINT FK_USERS_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

