package com.ai.task.repository;

import com.ai.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends    JpaRepository<Task, Long> {

    public Task findByTitle(String name);

    public List<Task> findByStatus(String status);


    public List<Task> findByStatusAndTitle(String status, String title);




}
