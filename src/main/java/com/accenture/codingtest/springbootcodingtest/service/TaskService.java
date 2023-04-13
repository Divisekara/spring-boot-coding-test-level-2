package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.TaskEntity;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskListDTO;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    //todo = add logs wherever needed

    @Autowired
    private TaskRepository taskRepository;

    public TaskListDTO getAllTasks() throws IOException {
        TaskListDTO taskList = new TaskListDTO();
        List<TaskEntity> taskEntity = taskRepository.findAll();

        // todo - return 400 error with response body if taskEntity is empty

        for (TaskEntity u: taskEntity ){
            TaskDTO task = new TaskDTO();
            task.setId(u.getId().toString());
            task.setDescription(u.getDescription());
            task.setTitle(u.getTitle());
            task.setStatus(u.getStatus());
            task.setProject_id(u.getProject_id().toString());
            task.setUser_id(u.getUser_id().toString());

            taskList.getData().add(task);
        }

        return taskList;
    }

    public TaskDTO getTaskByID(String taskId) throws IOException {
        TaskDTO task = new TaskDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("task doesn't exist");
        } else {
            task.setTitle(taskEntity.get().getTitle());
            task.setDescription(taskEntity.get().getDescription());
            task.setId(taskEntity.get().getId().toString());
            task.setStatus(taskEntity.get().getStatus());
            task.setProject_id(taskEntity.get().getProject_id().toString());
            task.setUser_id(taskEntity.get().getUser_id().toString());
        }
        return task;
    }

    public TaskDTO getTaskByTaskname(String taskname) throws IOException {
        TaskDTO task = new TaskDTO();

        // todo - need to implement a custom method
        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskname));
        if(taskEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("task doesn't exist");
        } else {
            task.setTitle(taskEntity.get().getTitle());
            task.setDescription(taskEntity.get().getDescription());
            task.setId(taskEntity.get().getId().toString());
            task.setStatus(taskEntity.get().getStatus());
            task.setProject_id(taskEntity.get().getProject_id().toString());
            task.setUser_id(taskEntity.get().getUser_id().toString());
        }
        return task;
    }

    public SuccessDTO createTask(TaskDTO task) throws IOException {

        // create taskEntity from request
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(task.getStatus());
        taskEntity.setProject_id(UUID.fromString(task.getProject_id()));
        taskEntity.setUser_id(UUID.fromString(task.getUser_id()));
        // no need to set id

        //todo = if task name already exists it returns an error then catch it and throw an error instead 500 internal server error

        TaskEntity x = taskRepository.save(taskEntity);
        // take id from the response from repository
        taskEntity.setId(x.getId());
        //todo- logging needs

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setId(taskEntity.getId().toString());
        return successDTO;
    }

    public SuccessDTO updateTask(String taskId, TaskDTO task) throws IOException {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("task doesn't exist");
        } else {
            // updating the task entity for the updated record
            taskEntity.get().setTitle(task.getTitle());
            taskEntity.get().setDescription(task.getDescription());
            taskEntity.get().setStatus(task.getStatus());
            taskEntity.get().setProject_id(UUID.fromString(task.getProject_id()));
            taskEntity.get().setUser_id(UUID.fromString(task.getUser_id()));
        }

        // update the task
        taskRepository.save(taskEntity.get());
        //todo- logging needs

        successDTO.setId(taskEntity.get().getId().toString());
        return successDTO;
    }

    public SuccessDTO patchTask(String taskId, TaskDTO task) throws IOException {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("task doesn't exist");
        } else {
            // updating the task entity for the updated record
            if(task.getTitle() != null){taskEntity.get().setTitle(task.getTitle());}
            if(task.getDescription() != null){taskEntity.get().setDescription(task.getDescription());}
            if(task.getStatus() != null){taskEntity.get().setStatus(task.getStatus());}
            if(task.getProject_id() != null){taskEntity.get().setProject_id(UUID.fromString(task.getProject_id()));}
            if(task.getUser_id() != null){taskEntity.get().setUser_id(UUID.fromString(task.getUser_id()));}
        }

        // update the task
        taskRepository.save(taskEntity.get());
        //todo- logging needs

        successDTO.setId(taskEntity.get().getId().toString());
        return successDTO;
    }

    public void deleteTask(String taskId) throws IOException {
        if(!taskRepository.existsById(UUID.fromString(taskId))){
            throw new IOException("task does not exist");
        }
        taskRepository.deleteById(UUID.fromString(taskId));
        //todo- logging needs
    }

}
