package com.ai.task.controller;

import com.ai.task.entity.Task;
import com.ai.task.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        logger.info("POST /api/tasks - Creating new task");
        Task createdTask = taskService.createTask(task);
        logger.info("POST /api/tasks - Task created with id: {}", createdTask.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        logger.info("PUT /api/tasks/{} - Updating task", id);
        Task updatedTask = taskService.updateTask(id, task);
        logger.info("PUT /api/tasks/{} - Task updated successfully", id);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        logger.info("GET /api/tasks/{} - Retrieving task", id);
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<Collection<Task>> getAllTasks() {
        logger.info("GET /api/tasks - Retrieving all tasks");
        Collection<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        logger.info("DELETE /api/tasks/{} - Deleting task", id);
        String result = taskService.deleteTask(id);
        logger.info("DELETE /api/tasks/{} - Task deleted successfully", id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/title")
    public ResponseEntity<Collection<Task>> findByTitle(@RequestParam String title) {
        logger.info("GET /api/tasks/title - Searching tasks by title: {}", title);
        Collection<Task> tasks = taskService.findByTitle(title);
        return ResponseEntity.ok(tasks);
    }






}
