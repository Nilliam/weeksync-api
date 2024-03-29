CREATE TABLE task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR,
    description VARCHAR,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    status VARCHAR,
    account_id UUID REFERENCES account(id),
    parent_id UUID REFERENCES task(id),
    CONSTRAINT task_account_id_foreign_key FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT task_parent_id_foreign_key FOREIGN KEY (parent_id) REFERENCES task(id)
);