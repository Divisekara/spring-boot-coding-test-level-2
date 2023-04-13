package com.accenture.codingtest.springbootcodingtest.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="acn.users")
@Getter
@Setter
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public UserEntity(String username, String password){
        this.username = username;
        this.password = password;
    }
}
