CREATE TABLE task_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR,
    description VARCHAR,
    status VARCHAR,
    task_id UUID REFERENCES task(id),
    date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT task_history_task_id_foreign_key FOREIGN KEY (task_id) REFERENCES task(id)
);