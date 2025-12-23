--liquibase formatted sql

--changeset alphamar:001-create-user-table
CREATE TABLE IF NOT EXISTS t_app_user (
    id BIGINT NOT NULL,
    business_id UUID NOT NULL,

    gender VARCHAR(5) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,

    email VARCHAR(150) NOT NULL,
    telephone VARCHAR(15) NOT NULL,
    telephone_checked BOOLEAN NOT NULL DEFAULT FALSE,

    -- security fields
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,

    -- audit fields
    created_by VARCHAR(150) NOT NULL,
    modified_by VARCHAR(150) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- index will be created automatically on unique constraints
    CONSTRAINT pk_app_user PRIMARY KEY (id),
    CONSTRAINT uq_user_business_id UNIQUE (business_id),
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT uq_user_telephone UNIQUE (telephone)
);

CREATE SEQUENCE IF NOT EXISTS seq_app_user START 1
    INCREMENT BY 75
    MINVALUE 1
    NO MAXVALUE
    CACHE 75
    NO CYCLE
    OWNED BY t_app_user.id;
--rollback DROP TABLE IF EXISTS t_task;
--rollback DROP SEQUENCE IF EXISTS seq_task;
