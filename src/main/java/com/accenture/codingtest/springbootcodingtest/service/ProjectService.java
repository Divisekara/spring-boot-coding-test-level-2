package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.ProjectEntity;
import com.accenture.codingtest.springbootcodingtest.model.common.SuccessDTO;
import com.accenture.codingtest.springbootcodingtest.model.project.ProjectDTO;
import com.accenture.codingtest.springbootcodingtest.model.project.ProjectListDTO;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    //todo = add logs wherever needed

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectListDTO getAllProjects() throws IOException {
        ProjectListDTO projectList = new ProjectListDTO();
        List<ProjectEntity> projectEntity = projectRepository.findAll();

        // todo - return 400 error with response body if projectEntity is empty

        for (ProjectEntity u: projectEntity ){
            ProjectDTO project = new ProjectDTO();
            project.setId(u.getId().toString());
            project.setName(u.getName());

            projectList.getData().add(project);
        }

        return projectList;
    }

    public ProjectDTO getProjectByID(String projectId) throws IOException {
        ProjectDTO project = new ProjectDTO();

        Optional<ProjectEntity> projectEntity = projectRepository.findById(UUID.fromString(projectId));
        if(projectEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("project doesn't exist");
        } else {
            project.setName(projectEntity.get().getName());
            project.setId(projectEntity.get().getId().toString());
        }
        return project;
    }

    public ProjectDTO getProjectByProjectname(String projectname) throws IOException {
        ProjectDTO project = new ProjectDTO();

        // todo - need to implement a custom method
        Optional<ProjectEntity> projectEntity = projectRepository.findById(UUID.fromString(projectname));
        if(projectEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("project doesn't exist");
        } else {
            project.setName(projectEntity.get().getName());
            project.setId(projectEntity.get().getId().toString());
        }
        return project;
    }

    public SuccessDTO createProject(ProjectDTO project) throws IOException {

        // create projectEntity from request
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(project.getName());
        // no need to set id

        //todo = if project name already exists it returns an error then catch it and throw an error instead 500 internal server error

        ProjectEntity x = projectRepository.save(projectEntity);
        // take id from the response from repository
        projectEntity.setId(x.getId());
        //todo- logging needs

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setId(projectEntity.getId().toString());
        return successDTO;
    }

    public SuccessDTO updateProject(String projectId, ProjectDTO project) throws IOException {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<ProjectEntity> projectEntity = projectRepository.findById(UUID.fromString(projectId));
        if(projectEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("project doesn't exist");
        } else {
            // updating the project entity for the updated record
            projectEntity.get().setName(project.getName());
        }

        // update the project
        projectRepository.save(projectEntity.get());
        //todo- logging needs

        successDTO.setId(projectEntity.get().getId().toString());
        return successDTO;
    }

    public SuccessDTO patchProject(String projectId, ProjectDTO project) throws IOException {
        SuccessDTO successDTO = new SuccessDTO();

        Optional<ProjectEntity> projectEntity = projectRepository.findById(UUID.fromString(projectId));
        if(projectEntity.isEmpty()) {
            //todo- logging needs
            throw new IOException("project doesn't exist");
        } else {
            // updating the project entity for the updated record
            if(project.getName() != null){projectEntity.get().setName(project.getName());}
        }

        // update the project
        projectRepository.save(projectEntity.get());
        //todo- logging needs

        successDTO.setId(projectEntity.get().getId().toString());
        return successDTO;
    }

    public void deleteProject(String projectId) throws IOException {
        if(!projectRepository.existsById(UUID.fromString(projectId))){
            throw new IOException("project does not exist");
        }
        projectRepository.deleteById(UUID.fromString(projectId));
        //todo- logging needs
    }

}
