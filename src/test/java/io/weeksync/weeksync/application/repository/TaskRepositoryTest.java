package io.weeksync.weeksync.application.repository;

import io.weeksync.weeksync.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

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

}
