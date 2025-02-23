package com.ayesh.todo.controller;

import com.ayesh.todo.entity.Task;
import com.ayesh.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public Page<Task> getTasks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        return taskService.getTasks(page, size);
    }

    @PatchMapping("/{id}")
    public Task markTaskAsCompleted(@PathVariable Integer id) {
        return taskService.markTaskAsCompleted(id);
    }
}
