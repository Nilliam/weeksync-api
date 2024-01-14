package io.weeksync.weeksync.application.repository;

import io.weeksync.weeksync.domain.model.Task;
import io.weeksync.weeksync.presentation.dto.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(value = """
            SELECT t.id, t.title, t.description, t.created_at AS "createdAt", t.status 
            FROM task t 
            WHERE EXTRACT(WEEK FROM t.created_at) = EXTRACT(WEEK FROM CAST(:date AS DATE)) 
            AND EXTRACT(YEAR FROM t.created_at) = EXTRACT(YEAR FROM CAST(:date AS DATE))
            """, nativeQuery = true)
    List<TaskDto> findTasksByWeekDate(@Param("date") LocalDate date);

}
