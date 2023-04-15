package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.domain.enums.ROLES;
import com.accenture.codingtest.springbootcodingtest.domain.enums.STATUSES;
import com.accenture.codingtest.springbootcodingtest.entity.TaskEntity;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskDTO;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.openMocks;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
        openMocks(this);
    }
    @Test
    void updateTask() throws Exception {

        UUID uuid =UUID.randomUUID();
        Optional<TaskEntity> taskEntity = Optional.ofNullable(om.readValue("{ \"id\": \"17e841ca-52bb-4356-a8a9-b880bd4d56c9\", \"title\": \"test_c380fae4c00a\", \"description\": \"test_e81419682f30\", \"status\": \"test_41aa9e1441b8\", \"project_id\": \"f13a65f0-0400-4420-b4fd-8a023d734413\", \"user_id\": \"28d17448-a3a1-4f68-8b57-36b19da391d7\" }", TaskEntity.class));
        taskEntity.get().setStatus(STATUSES.IN_PROGRESS.toString());

        TaskDTO taskDTO = om.readValue("{ \"id\": \"test_f6df0c7c3c42\", \"title\": \"test_5eb551d1adcc\", \"description\": \"test_87fada7071e4\", \"status\": \"test_72fcd47138a7\", \"project_id\": \"test_96b705a51076\", \"user_id\": \"test_9a45d63df21a\" }",TaskDTO.class);

        taskDTO.setStatus(STATUSES.IN_PROGRESS.toString());
        taskDTO.setProject_id(taskEntity.get().getProject_id().toString());
        taskDTO.setUser_id(UUID.randomUUID().toString());
        taskDTO.setTitle(taskEntity.get().getTitle());
        taskDTO.setDescription(taskEntity.get().getDescription());

        Mockito.doReturn(taskEntity).when(taskRepository).findById(any());
        taskService.updateTask(uuid.toString(),taskDTO,"product_owner");
    }

    @Test
    void updateTask2() throws Exception {

        UUID uuid =UUID.randomUUID();
        Optional<TaskEntity> taskEntity = Optional.ofNullable(om.readValue("{ \"id\": \"17e841ca-52bb-4356-a8a9-b880bd4d56c9\", \"title\": \"test_c380fae4c00a\", \"description\": \"test_e81419682f30\", \"status\": \"test_41aa9e1441b8\", \"project_id\": \"f13a65f0-0400-4420-b4fd-8a023d734413\", \"user_id\": \"28d17448-a3a1-4f68-8b57-36b19da391d7\" }", TaskEntity.class));
        taskEntity.get().setStatus(STATUSES.IN_PROGRESS.toString());

        TaskDTO taskDTO = om.readValue("{ \"id\": \"test_f6df0c7c3c42\", \"title\": \"test_5eb551d1adcc\", \"description\": \"test_87fada7071e4\", \"status\": \"test_72fcd47138a7\", \"project_id\": \"test_96b705a51076\", \"user_id\": \"test_9a45d63df21a\" }",TaskDTO.class);

        taskDTO.setStatus(STATUSES.IN_PROGRESS.toString());
        taskDTO.setProject_id(taskEntity.get().getProject_id().toString());
        taskDTO.setUser_id(UUID.randomUUID().toString());
        taskDTO.setTitle(taskEntity.get().getTitle());
        taskDTO.setDescription(taskEntity.get().getDescription());

        Mockito.doReturn(taskEntity).when(taskRepository).findById(any());

        Exception exception= assertThrows(Exception.class, () -> {
            taskService.updateTask(uuid.toString(), taskDTO, "admin");
        });
    }

    @Test
    void updateTask3() throws Exception {

        UUID uuid =UUID.randomUUID();
        Optional<TaskEntity> taskEntity = Optional.ofNullable(om.readValue("{ \"id\": \"17e841ca-52bb-4356-a8a9-b880bd4d56c9\", \"title\": \"test_c380fae4c00a\", \"description\": \"test_e81419682f30\", \"status\": \"test_41aa9e1441b8\", \"project_id\": \"f13a65f0-0400-4420-b4fd-8a023d734413\", \"user_id\": \"28d17448-a3a1-4f68-8b57-36b19da391d7\" }", TaskEntity.class));
        taskEntity.get().setStatus(STATUSES.IN_PROGRESS.toString());

        TaskDTO taskDTO = om.readValue("{ \"id\": \"test_f6df0c7c3c42\", \"title\": \"test_5eb551d1adcc\", \"description\": \"test_87fada7071e4\", \"status\": \"test_72fcd47138a7\", \"project_id\": \"test_96b705a51076\", \"user_id\": \"test_9a45d63df21a\" }",TaskDTO.class);

        taskDTO.setStatus("wrong test");
        taskDTO.setProject_id(taskEntity.get().getProject_id().toString());
        taskDTO.setUser_id(UUID.randomUUID().toString());
        taskDTO.setTitle(taskEntity.get().getTitle());
        taskDTO.setDescription(taskEntity.get().getDescription());

        Mockito.doReturn(taskEntity).when(taskRepository).findById(any());

        Exception exception= assertThrows(Exception.class, () -> {
            taskService.updateTask(uuid.toString(), taskDTO, "product_owner");
        });
    }

    @Test
    void updateTask4() throws Exception {
        UUID uuid =UUID.randomUUID();
        Optional<TaskEntity> taskEntity = Optional.ofNullable(om.readValue("{ \"id\": \"17e841ca-52bb-4356-a8a9-b880bd4d56c9\", \"title\": \"test_c380fae4c00a\", \"description\": \"test_e81419682f30\", \"status\": \"test_41aa9e1441b8\", \"project_id\": \"f13a65f0-0400-4420-b4fd-8a023d734413\", \"user_id\": \"28d17448-a3a1-4f68-8b57-36b19da391d7\" }", TaskEntity.class));
        taskEntity.get().setStatus(STATUSES.IN_PROGRESS.toString());

        TaskDTO taskDTO = om.readValue("{ \"id\": \"test_f6df0c7c3c42\", \"title\": \"test_5eb551d1adcc\", \"description\": \"test_87fada7071e4\", \"status\": \"test_72fcd47138a7\", \"project_id\": \"test_96b705a51076\", \"user_id\": \"test_9a45d63df21a\" }",TaskDTO.class);

        taskDTO.setStatus(STATUSES.IN_PROGRESS.toString());
        taskDTO.setProject_id(taskEntity.get().getProject_id().toString());
        taskDTO.setUser_id(UUID.randomUUID().toString());
        taskDTO.setTitle(taskEntity.get().getTitle());
        taskDTO.setDescription("description doesn't tally");

        Mockito.doReturn(taskEntity).when(taskRepository).findById(any());

        Exception exception= assertThrows(Exception.class, () -> {
            taskService.updateTask(uuid.toString(), taskDTO, ROLES.DEFAULT_USER.toString());
        });
    }
}