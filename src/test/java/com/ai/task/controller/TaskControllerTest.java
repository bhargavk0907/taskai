package com.ai.task.controller;

import com.ai.task.entity.Task;
import com.ai.task.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_Success() throws Exception {
        Task task = Task.builder().title("Test").status("PENDING").build();
        Task savedTask = Task.builder().id(1L).title("Test").status("PENDING").build();

        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void getTaskById_Success() throws Exception {
        Task task = Task.builder().id(1L).title("Test").status("PENDING").build();

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void getAllTasks_Success() throws Exception {
        List<Task> tasks = Arrays.asList(
            Task.builder().id(1L).title("Task1").status("PENDING").build(),
            Task.builder().id(2L).title("Task2").status("COMPLETED").build()
        );

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task1"));
    }

    @Test
    void updateTask_Success() throws Exception {
        Task updatedTask = Task.builder().id(1L).title("Updated").status("COMPLETED").build();

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void deleteTask_Success() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn("Task deleted with id: 1");

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted with id: 1"));
    }

    @Test
    void findByTitle_Success() throws Exception {
        List<Task> tasks = Arrays.asList(
            Task.builder().id(1L).title("Test").status("PENDING").build()
        );

        when(taskService.findByTitle("Test")).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/title").param("title", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test"));
    }
}