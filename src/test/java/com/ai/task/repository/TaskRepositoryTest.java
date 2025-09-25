package com.ai.task.repository;

import com.ai.task.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByTitle_Success() {
        Task task1 = Task.builder().title("Test Task").status("PENDING").description("Description").build();
        Task task2 = Task.builder().title("Test Task").status("COMPLETED").description("Another").build();
        Task task3 = Task.builder().title("Different").status("PENDING").description("Other").build();
        
        entityManager.persistAndFlush(task1);
        entityManager.persistAndFlush(task2);
        entityManager.persistAndFlush(task3);

        List<Task> result = taskRepository.findByTitle("Test Task");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(task -> "Test Task".equals(task.getTitle())));
    }

    @Test
    void findByTitle_NotFound() {
        List<Task> result = taskRepository.findByTitle("Non-existent");
        
        assertTrue(result.isEmpty());
    }

    @Test
    void findByStatus_Success() {
        Task task1 = Task.builder().title("Task1").status("PENDING").description("Desc1").build();
        Task task2 = Task.builder().title("Task2").status("PENDING").description("Desc2").build();
        Task task3 = Task.builder().title("Task3").status("COMPLETED").description("Desc3").build();
        
        entityManager.persistAndFlush(task1);
        entityManager.persistAndFlush(task2);
        entityManager.persistAndFlush(task3);

        List<Task> result = taskRepository.findByStatus("PENDING");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(task -> "PENDING".equals(task.getStatus())));
    }

    @Test
    void findByStatusAndTitle_Success() {
        Task task1 = Task.builder().title("Test").status("PENDING").description("Desc1").build();
        Task task2 = Task.builder().title("Test").status("COMPLETED").description("Desc2").build();
        Task task3 = Task.builder().title("Other").status("PENDING").description("Desc3").build();
        
        entityManager.persistAndFlush(task1);
        entityManager.persistAndFlush(task2);
        entityManager.persistAndFlush(task3);

        List<Task> result = taskRepository.findByStatusAndTitle("PENDING", "Test");

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
        assertEquals("PENDING", result.get(0).getStatus());
    }

    @Test
    void save_Success() {
        Task task = Task.builder().title("New Task").status("PENDING").description("Description").build();
        
        Task savedTask = taskRepository.save(task);
        
        assertNotNull(savedTask.getId());
        assertEquals("New Task", savedTask.getTitle());
        assertEquals("PENDING", savedTask.getStatus());
    }

    @Test
    void findById_Success() {
        Task task = Task.builder().title("Test").status("PENDING").description("Description").build();
        Task savedTask = entityManager.persistAndFlush(task);
        
        Optional<Task> result = taskRepository.findById(savedTask.getId());
        
        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getTitle());
    }

    @Test
    void deleteById_Success() {
        Task task = Task.builder().title("Test").status("PENDING").description("Description").build();
        Task savedTask = entityManager.persistAndFlush(task);
        
        taskRepository.deleteById(savedTask.getId());
        
        Optional<Task> result = taskRepository.findById(savedTask.getId());
        assertFalse(result.isPresent());
    }
}