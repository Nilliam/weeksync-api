package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskHistoryRepository;
import io.weeksync.weeksync.application.repository.TaskRepository;
import io.weeksync.weeksync.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceTest {

    private final TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private TaskHistoryService taskHistoryService;


    @Autowired
    public TaskServiceTest(final TaskService taskService) {
        this.taskService = Objects.requireNonNull(taskService);
    }

    @Test
    public void testSave() {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        when(taskRepository.save(task)).thenReturn(task);
        when(taskHistoryService.saveTaskHistory(task)).thenReturn(any());

        Task result = taskService.save(task);

        assertEquals(task, result);
    }
}