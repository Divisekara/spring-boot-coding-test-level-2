package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.user.UserDTO;
import com.accenture.codingtest.springbootcodingtest.model.user.UserListDTO;
import com.accenture.codingtest.springbootcodingtest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// time series data output
import io.micrometer.core.annotation.Timed;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Timed(value = "get.users.all.time", description = "time taken to get all users")
    @GetMapping("")
    public ResponseEntity<UserListDTO> GetAllUsers() throws IOException {
        logger.info("Request received to get all users endpoint");
        UserListDTO userList =  userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @Timed(value = "get.user.by.userid.time", description = "time taken to get user by user id")
    @GetMapping("/{user-id}")
    public ResponseEntity<UserDTO> GetUserByID(@PathVariable("user-id") final String userId) throws IOException {
        logger.info("Request received to get user by id endpoint");
        UserDTO user =  userService.getUserByID(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Timed(value = "get.user.by.username.time", description = "time taken to get user by username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> GetUserByUsername(@PathVariable("username") final String username) throws IOException {
        logger.info("Request received to get user by id endpoint");
        UserDTO user =  userService.getUserByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Timed(value = "create.user.time", description = "time taken to create user")
    @PostMapping("")
    public ResponseEntity<SuccessDTO> CreateUser(@RequestBody UserDTO request) throws IOException {
        logger.info("Request received to create user by id endpoint");
        SuccessDTO successDTO = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(successDTO);
    }

    @Timed(value = "update.user.by.id.time", description = "time taken to patch a user")
    @PutMapping("/{user-id}")
    public ResponseEntity<SuccessDTO> UpdateUserByID(@PathVariable("user-id") final String userId, @RequestBody UserDTO request) throws IOException {
        logger.info("Request received to update user by id endpoint");
        SuccessDTO successDTO = userService.updateUser(userId, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "patch.user.by.id.time", description = "time taken to patch a user")
    @PatchMapping("/{user-id}")
    public ResponseEntity<SuccessDTO> PatchUserByID(@PathVariable("user-id") final String userId, @RequestBody UserDTO request) throws IOException {
        logger.info("Request received to update user by id endpoint");
        SuccessDTO successDTO = userService.patchUser(userId, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "delete.user.by.id.time", description = "time taken to delete a user")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Object> DeleteUserByID(@PathVariable("user-id") final String userId) throws IOException {
        logger.info("Request received to delete user by id endpoint");
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
