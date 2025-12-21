--liquibase formatted sql

--changeset alphamar:001-create-task-table
CREATE TABLE IF NOT EXISTS t_tasks (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    business_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(50) NOT NULL,

    CONSTRAINT uq_task_business_id UNIQUE (business_id)
);

CREATE INDEX idx_tasks_title ON t_tasks(title);
--rollback DROP TABLE IF EXISTS t_task;
