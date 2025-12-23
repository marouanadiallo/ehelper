--liquibase formatted sql

--changeset alphamar:001-create-task-table
CREATE TABLE IF NOT EXISTS t_tasks (
    id BIGINT NOT NULL ,
    business_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(50) NOT NULL,

    -- audit fields
    created_by VARCHAR(150) NOT NULL,
    modified_by VARCHAR(150) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_task PRIMARY KEY (id),
    CONSTRAINT uq_task_business_id UNIQUE (business_id)
);
CREATE SEQUENCE IF NOT EXISTS seq_tasks START 1
    INCREMENT BY 75
    MINVALUE 1
    NO MAXVALUE
    CACHE 75
    NO CYCLE
    OWNED BY t_tasks.id;

CREATE INDEX idx_tasks_title ON t_tasks(title);
--rollback DROP TABLE IF EXISTS t_task;
--rollback DROP SEQUENCE IF EXISTS seq_task;
--rollback DROP INDEX IF EXISTS idx_tasks_title;
