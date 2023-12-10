package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskRepository;
import io.weeksync.weeksync.domain.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
