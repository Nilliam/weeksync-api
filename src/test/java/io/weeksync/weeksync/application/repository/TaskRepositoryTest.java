package io.weeksync.weeksync.application.repository;

import io.weeksync.weeksync.domain.model.Task;
import io.weeksync.weeksync.presentation.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test-containers")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSave() {
        // Given
        Task task = new Task();
        task.setId(UUID.randomUUID());

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertNotNull(savedTask);
        assertEquals(task.getId(), savedTask.getId());
    }

    @Test
    public void testFindTasksByWeekDate() {
        // Given
        Task task1 = new Task();
        task1.setCreatedAt(LocalDate.of(2024, 1, 15).atStartOfDay());
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setCreatedAt(LocalDate.of(2024, 1, 21).atStartOfDay());
        taskRepository.save(task2);

        // When
        List<TaskDto> tasks =
                taskRepository.findTasksByWeekDate(
                        LocalDate.of(2024, 1, 15)
                );

        // Then
        assertEquals(2, tasks.size());
        assertEquals(task1.getId(), tasks.get(0).getId());
        assertEquals(task2.getId(), tasks.get(1).getId());
    }

}
