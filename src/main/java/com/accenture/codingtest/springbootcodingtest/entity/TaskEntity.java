package com.accenture.codingtest.springbootcodingtest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="acn.tasks")
@Getter
@Setter
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private UUID project_id;

    @Column(nullable = false)
    private UUID user_id;

    public TaskEntity(String title, String description, String status, UUID  project_id, UUID user_id ){
        this.title = title;
        this.description = description;
        this.status = status;
        this.project_id = project_id;
        this.user_id = user_id;
    }
}
