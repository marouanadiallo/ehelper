--liquibase formatted sql

--changeset alphamar:001-create-task-table
CREATE TABLE IF NOT EXISTS t_tasks (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    business_key VARCHAR(100) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(50) NOT NULL
);
--rollback DROP TABLE IF EXISTS t_task;
