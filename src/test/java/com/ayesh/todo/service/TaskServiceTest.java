package com.ayesh.todo.service;

import com.ayesh.todo.entity.Task;
import com.ayesh.todo.repo.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LocalDateTime createdAt = LocalDateTime.now();

    @Test
    void createTask() {
        // Given
        Task task = new Task("New Task", "Test Description", false, createdAt);
        when(taskRepository.save(any(Task.class))).thenReturn(new Task("New Task", "Test Description", false, createdAt));

        // When
        Task createdTask = taskService.createTask(task).getBody();

        // Then
        assertNotNull(createdTask);
        assertEquals(1, createdTask.getId());
        assertEquals("New Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getTasks() {
        // Given
        Page<Task> mockPage = new PageImpl<>(List.of(
                new Task("Task 1", "Desc 1", false, createdAt),
                new Task("Task 2", "Desc 2", false, createdAt)
        ));
        when(taskRepository.findByCompletedFalseOrderByCreatedAtDesc(any(PageRequest.class)))
                .thenReturn(mockPage);

        // When
        Page<Task> tasks = taskService.getTasks(0, 5);

        // Then
        assertEquals(2, tasks.getContent().size());
        verify(taskRepository, times(1)).findByCompletedFalseOrderByCreatedAtDesc(any(PageRequest.class));
    }

    @Test
    void markTaskAsCompleted() {
        // Given
        Task task = new Task("Task 1", "Desc 1", false, createdAt);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(new Task("Task 1", "Desc 1", true, createdAt));

        // When
        Task updatedTask = taskService.markTaskAsCompleted(1);

        // Then
        assertTrue(updatedTask.isCompleted());
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(task);
    }
}