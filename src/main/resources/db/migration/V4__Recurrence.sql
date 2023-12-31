CREATE TABLE recurrence (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    recur_until DATE,
    type VARCHAR,
    template_task_id UUID REFERENCES task(id),
    CONSTRAINT recurrence_template_task_id_foreign_key FOREIGN KEY (template_task_id) REFERENCES task(id)
);