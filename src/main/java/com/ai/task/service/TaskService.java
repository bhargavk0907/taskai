package com.ai.task.service;

import com.ai.task.entity.Task;
import com.ai.task.exception.BadRequestException;
import com.ai.task.exception.TaskNotFoundException;
import com.ai.task.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private TaskRepository taskRepository;

   @Autowired
    public TaskService(TaskRepository taskRepository) {
       this.taskRepository = taskRepository;
   }

    public Task createTask(Task task) {
        logger.info("Creating new task");
        Task savedTask = taskRepository.save(task);
        logger.info("Task created successfully with id: {}", savedTask.getId());
        return savedTask;
    }

    public Task updateTask(Long id, Task task) {
        logger.info("Updating task with id: {}", id);
        taskRepository.findById(id).orElseThrow(() -> {
            logger.error("Task update failed: Task not found with id: {}", id);
            return new TaskNotFoundException(id);
        });
        Task updatedTask = taskRepository.save(task);
        logger.info("Task updated successfully with id: {}", id);
        return updatedTask;
    }

    public Task getTaskById(Long id) {
        logger.info("Retrieving task with id: {}", id);
        return taskRepository.findById(id).orElseThrow(() -> {
            logger.error("Task not found with id: {}", id);
            return new TaskNotFoundException(id);
        });
    }

    public Collection<Task> getAllTasks() {
        logger.info("Retrieving all tasks");
        Collection<Task> tasks = taskRepository.findAll();
        logger.info("Retrieved {} tasks", tasks.size());
        return tasks;
    }

    public String deleteTask(Long id) {
        logger.info("Deleting task with id: {}", id);
        taskRepository.findById(id).orElseThrow(() -> {
            logger.error("Task deletion failed: Task not found with id: {}", id);
            return new TaskNotFoundException(id);
        });
        taskRepository.deleteById(id);
        logger.info("Task deleted successfully with id: {}", id);
        return "Task deleted with id: " + id;
    }

    public List<Task> findByTitle(String title) {
        logger.info("Searching for tasks with title: {}", title);
        List<Task> tasks = taskRepository.findByTitle(title);
        logger.info("Found {} tasks with title: {}", tasks.size(), title);
       //logger.debug(title, tasks);
        return tasks;
    }



}
