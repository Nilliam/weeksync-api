package io.weeksync.weeksync.application.service;

import io.weeksync.weeksync.application.repository.TaskHistoryRepository;
import io.weeksync.weeksync.domain.model.Task;
import io.weeksync.weeksync.domain.model.TaskHistory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskHistoryServiceTest {

    private final TaskHistoryService taskHistoryService;

    @MockBean
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskHistoryServiceTest(final TaskHistoryService taskHistoryService) {
        this.taskHistoryService = Objects.requireNonNull(taskHistoryService);
    }

    @Test
    public void testSaveTaskHistory() {
        Task task = new Task();
        task.setId(UUID.randomUUID());

        ArgumentCaptor<TaskHistory> argumentCaptor = ArgumentCaptor.forClass(TaskHistory.class);
        when(taskHistoryRepository.save(argumentCaptor.capture())).thenAnswer(invocation -> argumentCaptor.getValue());

        TaskHistory result = taskHistoryService.saveTaskHistory(task);

        assertEquals(task, result.getTask());
    }
}