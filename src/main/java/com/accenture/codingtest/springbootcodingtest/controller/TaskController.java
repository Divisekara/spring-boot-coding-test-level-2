package com.accenture.codingtest.springbootcodingtest.controller;

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

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Timed(value = "get.tasks.all.time", description = "time taken to get all tasks")
    @GetMapping("")
    public ResponseEntity<TaskListDTO> GetAllTasks() throws IOException {
        logger.info("Request received to get all tasks endpoint");
        TaskListDTO taskList =  taskService.getAllTasks();

        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @Timed(value = "get.task.by.taskid.time", description = "time taken to get task by task id")
    @GetMapping("/{task-id}")
    public ResponseEntity<TaskDTO> GetTaskByID(@PathVariable("task-id") final String taskId) throws IOException {
        logger.info("Request received to get task by id endpoint");
        TaskDTO task =  taskService.getTaskByID(taskId);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @Timed(value = "get.task.by.taskname.time", description = "time taken to get task by taskname")
    @GetMapping("/taskname/{taskname}")
    public ResponseEntity<TaskDTO> GetTaskByTaskname(@PathVariable("taskname") final String taskname) throws IOException {
        logger.info("Request received to get task by id endpoint");
        TaskDTO task =  taskService.getTaskByTaskname(taskname);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @Timed(value = "create.task.time", description = "time taken to create task")
    @PostMapping("")
    public ResponseEntity<SuccessDTO> CreateTask(@RequestBody TaskDTO request) throws IOException {
        logger.info("Request received to create task by id endpoint");
        SuccessDTO successDTO = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(successDTO);
    }

    @Timed(value = "update.task.by.id.time", description = "time taken to patch a task")
    @PutMapping("/{task-id}")
    public ResponseEntity<SuccessDTO> UpdateTaskByID(@PathVariable("task-id") final String taskId, @RequestBody TaskDTO request) throws IOException {
        logger.info("Request received to update task by id endpoint");
        SuccessDTO successDTO = taskService.updateTask(taskId, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "patch.task.by.id.time", description = "time taken to patch a task")
    @PatchMapping("/{task-id}")
    public ResponseEntity<SuccessDTO> PatchTaskByID(@PathVariable("task-id") final String taskId, @RequestBody TaskDTO request) throws IOException {
        logger.info("Request received to update task by id endpoint");
        SuccessDTO successDTO = taskService.patchTask(taskId, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "delete.task.by.id.time", description = "time taken to delete a task")
    @DeleteMapping("/{task-id}")
    public ResponseEntity<Object> DeleteTaskByID(@PathVariable("task-id") final String taskId) throws IOException {
        logger.info("Request received to delete task by id endpoint");
        taskService.deleteTask(taskId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
