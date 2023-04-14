package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.domain.enums.ROLES;
import com.accenture.codingtest.springbootcodingtest.domain.enums.STATUSES;
import com.accenture.codingtest.springbootcodingtest.entity.TaskEntity;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskDTO;
import com.accenture.codingtest.springbootcodingtest.model.task.TaskListDTO;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    //todo = add logs wherever needed

    @Autowired
    private TaskRepository taskRepository;

    public TaskListDTO getAllTasks() throws Exception {
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

    public TaskDTO getTaskByID(String taskId) throws Exception {
        TaskDTO task = new TaskDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            logger.info("task doesn't exist for [{}]", taskId);
            throw new Exception("task doesn't exist");
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

    public TaskDTO getTaskByTaskname(String taskname) throws Exception {
        TaskDTO task = new TaskDTO();

        // todo - need to implement a custom method
        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskname));
        if(taskEntity.isEmpty()) {
            logger.info("task doesn't exist for [{}]", taskname);
            throw new Exception("task doesn't exist");
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

    public SuccessDTO createTask(TaskDTO task) throws Exception {

        // create taskEntity from request
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(STATUSES.NOT_STARTED.toString()); // default task
        taskEntity.setProject_id(UUID.fromString(task.getProject_id()));
        taskEntity.setUser_id(UUID.fromString(task.getUser_id()));
        // no need to set id

        TaskEntity x = taskRepository.save(taskEntity);
        logger.info("task created for [{}]", taskEntity);
        // take id from the response from repository
        taskEntity.setId(x.getId());

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setId(taskEntity.getId().toString());
        return successDTO;
    }

    public SuccessDTO updateTask(String taskId, TaskDTO task, String role) throws Exception {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            logger.info("task doesn't exist");
            throw new Exception("task doesn't exist");
        } else {
            // validating status updating process
            if (!validateStatus(task.getStatus(), taskEntity.get().getStatus(), role)){
                logger.info("status update error");
                throw new Exception("status update error");
            }

            // validating user assigning process
            if(!validateUserAssign(task.getUser_id(), taskEntity.get().getUser_id().toString(), role)){
                logger.info("cannot assign user [{}] to the task", task.getUser_id());
                throw new Exception("cannot assign user to the task");
            }

            // validate team member activity
            // Team member can only change the status of the task assigned to them, they can edit any other attribute in a task.
            if(!validateTeamMemberActivitiesInUpdate(task, taskEntity.get())){
                logger.info("cannot modify [{}] by default user role", taskEntity);
                throw new Exception("cannot modify the task by default user role");
            }

            // assign new values to task entity for update
            taskEntity.get().setTitle(task.getTitle());
            taskEntity.get().setDescription(task.getDescription());
            taskEntity.get().setStatus(task.getStatus());
            taskEntity.get().setProject_id(UUID.fromString(task.getProject_id()));
            taskEntity.get().setUser_id(UUID.fromString(task.getUser_id()));
        }

        // update the task
        taskRepository.save(taskEntity.get());
        logger.info("task update successfully [{}]", taskEntity.get());

        successDTO.setId(taskEntity.get().getId().toString());
        return successDTO;
    }

    public SuccessDTO patchTask(String taskId, TaskDTO task, String role) throws Exception {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<TaskEntity> taskEntity = taskRepository.findById(UUID.fromString(taskId));
        if(taskEntity.isEmpty()) {
            logger.info("task doesn't exist for [{}]", taskId);
            throw new Exception("task doesn't exist");
        } else {
            // Team member can only change the status of the task assigned to them, they can edit any other attribute in a task.
            if(role.toUpperCase().equals(ROLES.DEFAULT_USER.toString()) && !validateTeamMemberActivitiesInPatch(task)){
                logger.info("cannot modify [{}] by default user role", taskEntity);
                throw new Exception("cannot modify the task by default user role");
            }

            if(task.getStatus() != null){
                // validating status updating process
                if (!validateStatus(task.getStatus(), taskEntity.get().getStatus(), role)){
                    logger.info("status update error");
                    throw new Exception("status update error");
                }
                taskEntity.get().setStatus(task.getStatus());
            }

            if(task.getUser_id() != null){
                // validating user assigning process
                if(!validateUserAssign(task.getUser_id(), taskEntity.get().getUser_id().toString(), role)){
                    logger.info("cannot assign user [{}] to the task", task.getUser_id());
                    throw new Exception("cannot assign user to the task");
                }
                taskEntity.get().setUser_id(UUID.fromString(task.getUser_id()));
            }

            // updating the task entity for the updated record
            if(task.getTitle() != null){taskEntity.get().setTitle(task.getTitle());}
            if(task.getDescription() != null){taskEntity.get().setDescription(task.getDescription());}
            if(task.getProject_id() != null){taskEntity.get().setProject_id(UUID.fromString(task.getProject_id()));}
        }

        // update the task
        taskRepository.save(taskEntity.get());
        logger.info("task patched successfully [{}]", taskEntity.get());

        successDTO.setId(taskEntity.get().getId().toString());
        return successDTO;
    }

    public void deleteTask(String taskId) throws Exception {
        if(!taskRepository.existsById(UUID.fromString(taskId))){
            throw new Exception("task does not exist");
        }
        taskRepository.deleteById(UUID.fromString(taskId));
        logger.info("task [{}] deleted successful", taskId);
    }


    // validateStatus validate status updating process
    private boolean validateStatus(String newStatus, String prevStatus, String role) throws Exception{
        // check whether the status is updated
        if(Objects.equals(newStatus, prevStatus)) {
            // if status is not updated there's no point validating further
            return true;
        }

        // if status is updated then role need to be PRODUCT_OWNER else it's an error
        if(!Objects.equals(ROLES.PRODUCT_OWNER.toString(), role.toUpperCase())){
            return false;
            // throw new Exception("status cannot be updated");
        }

        // if the role is product_owner then need to validate whether the status is valid
        for (STATUSES s : STATUSES.values()){
            if (Objects.equals(s.toString(), newStatus)){
                return true;
            }
        }
        // if the new status is not valid then need to return false
        return false;
    }

    // validateUserAssign validate user assigning process
    private boolean validateUserAssign(String newUser, String oldUser, String role) throws Exception{
        // user is not updated
        if(Objects.equals(newUser, oldUser)){
            return true;
        }

        // Only PRODUCT_OWNER role can assign tasks to a team member in their project
        return Objects.equals(ROLES.PRODUCT_OWNER.toString(), role.toUpperCase());
    }

    // validateTeamMemberActivitiesInUpdate team member cannot modify any attribute other than status
    private boolean validateTeamMemberActivitiesInUpdate(TaskDTO newTask, TaskEntity oldTask)throws Exception{
        return Objects.equals(newTask.getTitle(), oldTask.getTitle()) &&
                Objects.equals(newTask.getDescription(), oldTask.getDescription()) &&
                Objects.equals(newTask.getProject_id(), oldTask.getProject_id().toString()) &&
                Objects.equals(newTask.getUser_id(), oldTask.getUser_id().toString());
    }

    // validateTeamMemberActivitiesInPatch team member cannot modify any attribute other than status
    private boolean validateTeamMemberActivitiesInPatch(TaskDTO newTask)throws Exception{
        return newTask.getTitle() == null &&
                newTask.getDescription() == null &&
                newTask.getProject_id() == null &&
                newTask.getUser_id() == null;
    }
}
