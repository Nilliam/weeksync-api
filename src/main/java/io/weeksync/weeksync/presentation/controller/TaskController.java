package io.weeksync.weeksync.presentation.controller;

import io.weeksync.weeksync.application.service.TaskService;
import io.weeksync.weeksync.domain.model.Task;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = Objects.requireNonNull(taskService);
    }

    @PostMapping
    public Task save(@RequestBody Task task) {
        return taskService.save(task);
    }

}
