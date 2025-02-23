package com.ayesh.todo.controller;

import com.ayesh.todo.entity.Task;
import com.ayesh.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private LocalDateTime createdAt = LocalDateTime.now();

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() {
        // Given
        Task task = new Task("New Task", "Test Description", false, createdAt);
        Task savedTask = new Task("New Task", "Test Description", false, createdAt);
        when(taskService.createTask(any(Task.class))).thenReturn(ResponseEntity.ok(savedTask));

        // When
        ResponseEntity<Task> response = taskController.createTask(task);

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("New Task", response.getBody().getTitle());
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    void getTasks() {
        // Given
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Description 1", false, createdAt),
                new Task("Task 2", "Description 2", true, createdAt)
        );

        Page<Task> taskPage = new PageImpl<>(tasks); // Wrap list in Page
        when(taskService.getTasks(1, 2)).thenReturn(taskPage); // Mock service to return Page<Task>

        // When
        Page<Task> response = taskController.getTasks(1, 2); // Expect Page<Task>

        // Then
        assertNotNull(response);
        assertEquals(2, response.getContent().size()); // Use getContent() for Page
        verify(taskService, times(1)).getTasks(1, 2);
    }


    @Test
    void markTaskAsCompleted() {
        // Given
        int taskId = 1;
        Task completedTask = new Task( "Task 1", "Description 1", true, createdAt);
        when(taskService.markTaskAsCompleted(taskId)).thenReturn(completedTask); // No ResponseEntity

        // When
        Task response = taskController.markTaskAsCompleted(taskId);

        // Then
        assertNotNull(response);
        assertNotNull(response.getTitle());
        assertTrue(response.isCompleted());
        verify(taskService, times(1)).markTaskAsCompleted(taskId);
    }
}