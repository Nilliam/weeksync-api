package io.weeksync.weeksync.application.repository;

import io.weeksync.weeksync.domain.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, UUID> {
}
