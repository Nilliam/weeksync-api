ALTER TABLE task ADD COLUMN recurrence_id UUID REFERENCES recurrence(id),
ADD CONSTRAINT task_recurrence_id_foreign_key FOREIGN KEY (recurrence_id) REFERENCES recurrence(id);
