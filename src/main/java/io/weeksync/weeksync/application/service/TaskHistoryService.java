package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskHistoryRepository;
import io.weeksync.weeksync.domain.model.Task;
import io.weeksync.weeksync.domain.model.TaskHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskHistoryService(final TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = Objects.requireNonNull(taskHistoryRepository);
    }

    public TaskHistory saveTaskHistory(Task task) {
        TaskHistory taskHistory = TaskHistory
                .builder()
                .task(task)
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(LocalDateTime.now())
                .build();
        return taskHistoryRepository.save(taskHistory);
    }

}
