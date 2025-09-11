package com.ai.task.service;

import com.ai.task.entity.Task;
import com.ai.task.exception.TaskNotFoundException;
import com.ai.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {

   private TaskRepository taskRepository;

   @Autowired
    public TaskService(TaskRepository taskRepository) {
       this.taskRepository = taskRepository;
   }

    public Task createTask(Task task) {
       return taskRepository.save(task);

        //taskStore.put(task.getId(), task);
       // return task;
    }

    public Task updateTask(Long id, Task task) {

       taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
//        if (!taskStore.containsKey(id)) {
//            throw new TaskNotFoundException(id);
//        }
//        return taskStore.get(id);

        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Collection<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public String deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
        return "Task deleted with id: " + id;
    }

    public Task findByTitle(String title) {
        System.out.println("Searching for task with title: " + title);
        return taskRepository.findByTitle(title);
    }



}
