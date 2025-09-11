package com.ai.task.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
//LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String status;



}
