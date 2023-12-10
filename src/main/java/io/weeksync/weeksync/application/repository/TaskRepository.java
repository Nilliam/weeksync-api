package io.weeksync.weeksync.application.repository;

import io.weeksync.weeksync.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
