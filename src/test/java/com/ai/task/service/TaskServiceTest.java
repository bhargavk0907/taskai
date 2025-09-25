package com.ai.task.service;

import com.ai.task.entity.Task;
import com.ai.task.exception.TaskNotFoundException;
import com.ai.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_Success() {
        Task task = Task.builder().title("Test").status("PENDING").build();
        Task savedTask = Task.builder().id(1L).title("Test").status("PENDING").build();
        
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        
        Task result = taskService.createTask(task);
        
        assertEquals(1L, result.getId());
        verify(taskRepository).save(task);
    }

    @Test
    void getTaskById_Success() {
        Task task = Task.builder().id(1L).title("Test").status("PENDING").build();
        
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        
        Task result = taskService.getTaskById(1L);
        
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getTitle());
    }

    @Test
    void getTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void getAllTasks_Success() {
        List<Task> tasks = Arrays.asList(
            Task.builder().id(1L).title("Task1").status("PENDING").build(),
            Task.builder().id(2L).title("Task2").status("COMPLETED").build()
        );
        
        when(taskRepository.findAll()).thenReturn(tasks);
        
        List<Task> result = (List<Task>) taskService.getAllTasks();
        
        assertEquals(2, result.size());
    }

    @Test
    void updateTask_Success() {
        Task existingTask = Task.builder().id(1L).title("Old").status("PENDING").build();
        Task updatedTask = Task.builder().id(1L).title("New").status("COMPLETED").build();
        
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        
        Task result = taskService.updateTask(1L, updatedTask);
        
        assertEquals("New", result.getTitle());
        assertEquals("COMPLETED", result.getStatus());
    }

    @Test
    void updateTask_NotFound() {
        Task task = Task.builder().title("Test").status("PENDING").build();
        
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, task));
    }

    @Test
    void deleteTask_Success() {
        Task task = Task.builder().id(1L).title("Test").status("PENDING").build();
        
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        
        String result = taskService.deleteTask(1L);
        
        assertEquals("Task deleted with id: 1", result);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
    }

    @Test
    void findByTitle_Success() {
        List<Task> tasks = Arrays.asList(
            Task.builder().id(1L).title("Test").status("PENDING").build()
        );
        
        when(taskRepository.findByTitle("Test")).thenReturn(tasks);
        
        List<Task> result = taskService.findByTitle("Test");
        
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }
}