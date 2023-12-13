package io.weeksync.weeksync.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.weeksync.weeksync.application.service.TaskService;
import io.weeksync.weeksync.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Autowired
    public TaskControllerTest(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        this.mockMvc = Objects.requireNonNull(mockMvc);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Test
    public void testSave() throws Exception {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        when(taskService.save(task)).thenReturn(task);

        String test = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(test))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId().toString())));
    }
}