package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.domain.enums.ROLES;
import com.accenture.codingtest.springbootcodingtest.model.common.ErrorResponse;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.project.ProjectDTO;
import com.accenture.codingtest.springbootcodingtest.model.project.ProjectListDTO;
import com.accenture.codingtest.springbootcodingtest.model.user.UserDTO;
import com.accenture.codingtest.springbootcodingtest.service.ProjectService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Timed(value = "get.projects.all.time", description = "time taken to get all projects")
    @GetMapping("")
    public ResponseEntity<Object> GetAllProjects() throws IOException {
        logger.info("Request received to get all projects endpoint");

        ProjectListDTO projectList;
        try {
            projectList =  projectService.getAllProjects();
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @Timed(value = "get.project.by.projectid.time", description = "time taken to get project by project id")
    @GetMapping("/{project-id}")
    public ResponseEntity<Object> GetProjectByID(@PathVariable("project-id") final String projectId) throws IOException {
        logger.info("Request received to get project by id endpoint");

        ProjectDTO project;
        try {
            project =  projectService.getProjectByID(projectId);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @Timed(value = "get.project.by.projectname.time", description = "time taken to get project by projectname")
    @GetMapping("/projectname/{projectname}")
    public ResponseEntity<Object> GetProjectByProjectname(@PathVariable("projectname") final String projectname) throws IOException {
        logger.info("Request received to get project by id endpoint");

        ProjectDTO project;
        try {
            project =  projectService.getProjectByProjectname(projectname);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @Timed(value = "create.project.time", description = "time taken to create project")
    @PostMapping("")
    public ResponseEntity<Object> CreateProject(@RequestBody ProjectDTO request, @RequestHeader("role") final String role) throws IOException {
        logger.info("Request received to create project by id endpoint");

        // Only PRODUCT_OWNER role can create a project
        if(!Objects.equals(ROLES.PRODUCT_OWNER.toString(), role.toUpperCase())){
            logger.info("User unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SuccessDTO successDTO;
        try {
            successDTO = projectService.createProject(request);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(successDTO);
    }

    @Timed(value = "update.project.by.id.time", description = "time taken to patch a project")
    @PutMapping("/{project-id}")
    public ResponseEntity<Object> UpdateProjectByID(@PathVariable("project-id") final String projectId, @RequestBody ProjectDTO request) throws IOException {
        logger.info("Request received to update project by id endpoint");

        SuccessDTO successDTO;
        try {
            successDTO = projectService.updateProject(projectId, request);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "patch.project.by.id.time", description = "time taken to patch a project")
    @PatchMapping("/{project-id}")
    public ResponseEntity<Object> PatchProjectByID(@PathVariable("project-id") final String projectId, @RequestBody ProjectDTO request) throws IOException {
        logger.info("Request received to update project by id endpoint");

        SuccessDTO successDTO;
        try {
            successDTO = projectService.patchProject(projectId, request);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(successDTO);
    }

    @Timed(value = "delete.project.by.id.time", description = "time taken to delete a project")
    @DeleteMapping("/{project-id}")
    public ResponseEntity<Object> DeleteProjectByID(@PathVariable("project-id") final String projectId) throws IOException {
        logger.info("Request received to delete project by id endpoint");

        try {
            projectService.deleteProject(projectId);
        } catch (IOException e){
            ErrorResponse error = new ErrorResponse();
            error.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
