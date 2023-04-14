package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.domain.enums.ROLES;
import com.accenture.codingtest.springbootcodingtest.model.common.ErrorResponse;
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
import java.util.Locale;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Timed(value = "get.users.all.time", description = "time taken to get all users")
    @GetMapping("")
    public ResponseEntity<Object> GetAllUsers(@RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to get all users endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserListDTO userList;
        try {
            userList = userService.getAllUsers();
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @Timed(value = "get.user.by.userid.time", description = "time taken to get user by user id")
    @GetMapping("/{user-id}")
    public ResponseEntity<Object> GetUserByID(@PathVariable("user-id") final String userId, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to get user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user;
        try {
            user =  userService.getUserByID(userId);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Timed(value = "get.user.by.username.time", description = "time taken to get user by username")
    @GetMapping("/username/{username}")
    public ResponseEntity<Object> GetUserByUsername(@PathVariable("username") final String username, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to get user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user;
        try {
            user =  userService.getUserByUsername(username);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Timed(value = "create.user.time", description = "time taken to create user")
    @PostMapping("")
    public ResponseEntity<Object> CreateUser(@RequestBody UserDTO request, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to create user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SuccessDTO successDTO;
        try{
            successDTO = userService.createUser(request);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(successDTO);
    }

    @Timed(value = "update.user.by.id.time", description = "time taken to patch a user")
    @PutMapping("/{user-id}")
    public ResponseEntity<Object> UpdateUserByID(@PathVariable("user-id") final String userId, @RequestBody UserDTO request, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to update user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SuccessDTO successDTO;
        try{
            successDTO = userService.updateUser(userId, request);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "patch.user.by.id.time", description = "time taken to patch a user")
    @PatchMapping("/{user-id}")
    public ResponseEntity<Object> PatchUserByID(@PathVariable("user-id") final String userId, @RequestBody UserDTO request, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to update user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SuccessDTO successDTO;
        try {
            successDTO = userService.patchUser(userId, request);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "delete.user.by.id.time", description = "time taken to delete a user")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Object> DeleteUserByID(@PathVariable("user-id") final String userId, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to delete user by id endpoint");

        if(!Objects.equals(ROLES.ADMIN.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
