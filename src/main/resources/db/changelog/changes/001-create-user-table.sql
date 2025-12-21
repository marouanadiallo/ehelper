--liquibase formatted sql

--changeset alphamar:001-create-user-table
CREATE TABLE IF NOT EXISTS t_app_user (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    business_id UUID NOT NULL,

    gender VARCHAR(5) NOT NULL,
    first_name VARCHAR(75) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,

    email VARCHAR(255) NOT NULL,
    telephone VARCHAR(50) NOT NULL,

    -- index will be created automatically on unique constraints
    CONSTRAINT uq_user_business_id UNIQUE (business_id),
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT uq_user_telephone UNIQUE (telephone)
);

--rollback DROP TABLE IF EXISTS t_task;
