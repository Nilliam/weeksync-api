package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskRepository;
import io.weeksync.weeksync.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskHistoryService taskHistoryService;

    @Autowired
    public TaskService(final TaskRepository taskRepository,
                       final TaskHistoryService taskHistoryService) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
        this.taskHistoryService = Objects.requireNonNull(taskHistoryService);
    }

    public Task save(Task task) {
        task = taskRepository.save(task);
        taskHistoryService.saveTaskHistory(task);
        return task;
    }

}
