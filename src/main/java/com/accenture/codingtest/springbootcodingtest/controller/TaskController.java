package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.domain.enums.ROLES;
import com.accenture.codingtest.springbootcodingtest.model.common.ErrorResponse;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskListDTO;
import com.accenture.codingtest.springbootcodingtest.service.TaskService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Timed(value = "get.tasks.all.time", description = "time taken to get all tasks")
    @GetMapping("")
    public ResponseEntity<Object> GetAllTasks() throws Exception {
        logger.info("Request received to get all tasks endpoint");

        TaskListDTO taskList;
        try {
            taskList =  taskService.getAllTasks();
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @Timed(value = "get.task.by.taskid.time", description = "time taken to get task by task id")
    @GetMapping("/{task-id}")
    public ResponseEntity<Object> GetTaskByID(@PathVariable("task-id") final String taskId) throws Exception {
        logger.info("Request received to get task by id endpoint");
        TaskDTO task;
        try {
            task =  taskService.getTaskByID(taskId);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }


        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @Timed(value = "get.task.by.taskname.time", description = "time taken to get task by taskname")
    @GetMapping("/taskname/{taskname}")
    public ResponseEntity<Object> GetTaskByTaskname(@PathVariable("taskname") final String taskname) throws Exception {
        logger.info("Request received to get task by id endpoint");
        TaskDTO task;
        try {
            task = taskService.getTaskByTaskname(taskname);
        } catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @Timed(value = "create.task.time", description = "time taken to create task")
    @PostMapping("")
    public ResponseEntity<Object> CreateTask(@RequestBody TaskDTO request, @RequestHeader("role") final String role) throws Exception {
        // Only PRODUCT_OWNER role can create a tasks
        if(!Objects.equals(ROLES.PRODUCT_OWNER.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Request received to create task by id endpoint");
        SuccessDTO successDTO;
        try{
            successDTO = taskService.createTask(request);
        } catch(Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(successDTO);
    }

    @Timed(value = "update.task.by.id.time", description = "time taken to patch a task")
    @PutMapping("/{task-id}")
    public ResponseEntity<Object> UpdateTaskByID(@PathVariable("task-id") final String taskId, @RequestBody TaskDTO request, @RequestHeader("role") final String role) throws Exception {
        logger.info("Request received to update task by id endpoint");
        SuccessDTO successDTO = new SuccessDTO();
        try {
            successDTO = taskService.updateTask(taskId, request, role);
        } catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "patch.task.by.id.time", description = "time taken to patch a task")
    @PatchMapping("/{task-id}")
    public ResponseEntity<Object> PatchTaskByID(@PathVariable("task-id") final String taskId, @RequestBody TaskDTO request, @RequestHeader("role") final String role) throws Exception {
        logger.info("Request received to update task by id endpoint");
        SuccessDTO successDTO;
        try {
            successDTO = taskService.patchTask(taskId, request, role);
        } catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "delete.task.by.id.time", description = "time taken to delete a task")
    @DeleteMapping("/{task-id}")
    public ResponseEntity<Object> DeleteTaskByID(@PathVariable("task-id") final String taskId) throws Exception {
        logger.info("Request received to delete task by id endpoint");
        try {
            taskService.deleteTask(taskId);
        } catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
