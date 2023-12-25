package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskHistoryRepository;
import io.weeksync.weeksync.application.repository.TaskRepository;
import io.weeksync.weeksync.domain.model.Task;
import io.weeksync.weeksync.domain.model.TaskHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskService(final TaskRepository taskRepository,
                       final TaskHistoryRepository taskHistoryRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
        this.taskHistoryRepository = Objects.requireNonNull(taskHistoryRepository);
    }

    public Task save(Task task) {
        saveTaskHistory(task);
        return taskRepository.save(task);
    }

    private void saveTaskHistory(Task task) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setTitle(task.getTitle());
        taskHistory.setDescription(task.getDescription());
        taskHistory.setStatus(task.getStatus());
        taskHistory.setDateTime(LocalDateTime.now());
        taskHistoryRepository.save(taskHistory);
    }

}
