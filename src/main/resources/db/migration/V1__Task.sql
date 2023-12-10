CREATE TABLE task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR
);