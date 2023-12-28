CREATE TABLE account (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR,
    password VARCHAR,
    email VARCHAR,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);