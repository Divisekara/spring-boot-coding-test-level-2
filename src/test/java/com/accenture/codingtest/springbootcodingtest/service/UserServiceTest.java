package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.UserEntity;
import com.accenture.codingtest.springbootcodingtest.model.user.UserDTO;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        openMocks(this);
    }

    @Test
    void createUser() throws Exception {
        // SuccessDTO successDTO = om.readValue("{ \"id\": \"test_69deb85a6481\" }",SuccessDTO.class);
        UserDTO userDTO=om.readValue("{ \"id\": \"test_afec034b556d\", \"username\": \"test_f0790fe3bac6\", \"password\": \"test_222df415b0b7\" }",UserDTO.class);
        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setUsername("userName");
        savedUserEntity.setPassword("password");
        savedUserEntity.setId(UUID.randomUUID());
        Mockito.doReturn(savedUserEntity).when(userRepository).save(any(UserEntity.class));
        userService.createUser(userDTO);
    }




}