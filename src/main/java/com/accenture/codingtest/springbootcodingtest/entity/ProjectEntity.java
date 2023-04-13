package com.accenture.codingtest.springbootcodingtest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="acn.projects")
@Getter
@Setter
@RequiredArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    public ProjectEntity(String name){
        this.name = name;
    }
}
