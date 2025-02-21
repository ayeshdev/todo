package com.ayesh.todo.service;


import com.ayesh.todo.entity.Task;
import com.ayesh.todo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getLatestTasks() {
        return taskRepository.findTopFiveByCompletedFalseOrderByCreatedAtDesc();
    }

    public Task markTaskAsCompleted(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
