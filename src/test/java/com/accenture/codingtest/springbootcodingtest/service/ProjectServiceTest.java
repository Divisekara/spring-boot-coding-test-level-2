package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.ProjectEntity;
import com.accenture.codingtest.springbootcodingtest.model.project.ProjectDTO;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    private ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        projectService = new ProjectService();
        openMocks(this);
    }

    @Test
    void createProject() throws Exception {
        ProjectEntity projectEntity = new ProjectEntity();
        ProjectDTO projectDTO = om.readValue("{ \"id\": \"test_a38bb30a066e\", \"name\": \"test_0efd07123f37\" }",ProjectDTO.class);
        projectEntity.setName("asitha");
        projectEntity.setId(UUID.randomUUID());
        Mockito.doReturn(projectEntity).when(projectRepository).save(any(ProjectEntity.class));
        projectService.createProject(projectDTO);

    }


}