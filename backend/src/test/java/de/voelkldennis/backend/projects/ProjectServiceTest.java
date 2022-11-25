package de.voelkldennis.backend.projects;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    ProjectUtils projectUtils = mock(ProjectUtils.class);
    ProjectRepo projectRepo = mock(ProjectRepo.class);
    ProjectService projectService = new ProjectService(projectUtils, projectRepo);


    @Test
    void addNewProject() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;
        Project projectWithId = new Project(projectId, projectName, shortDescription, projectVisibility);
        NewProjectDTO newProjectDTO = new NewProjectDTO(projectName, shortDescription, projectVisibility);
        //when
        when(projectUtils.generateUUID()).thenReturn(projectId);
        when(projectRepo.save(projectWithId)).thenReturn(projectWithId);
        Project actual = projectService.addNewProject(newProjectDTO);
        //then
        verify(projectUtils).generateUUID();
        verify(projectRepo).save(projectWithId);
        assertEquals(projectWithId, actual);
    }

    @Test
    void getAllProjects() {
        //given
        List<Project> testProject = new ArrayList<>();
        //when
        List<Project> actual = projectService.getAllProjects();
        //then
        assertEquals(testProject, actual);
    }

    @Test
    void getProjectWithId() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;
        Project projectWithId = new Project(projectId, projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.of(projectWithId));
        Optional<Project> actual = projectService.getProjectWithId(projectId);
        Optional<Project> expected = Optional.of(projectWithId);
        //then
        verify(projectRepo).findByProjectId(projectId);
        assertEquals(expected, actual);
    }

    @Test
    void deleteProjectWithId() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;
        Project projectWithId = new Project(projectId, projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.of(projectWithId));
        Project actual = projectService.deleteProjectWithId(projectId);
        //then
        verify(projectRepo).deleteByProjectId(projectId);
        assertNull(actual);
    }

    @Test
    void isProjectIdExistingReturnTrue() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;
        Project projectWithId = new Project(projectId, projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findAll()).thenReturn(List.of(projectWithId));
        boolean returnTrue = projectService.isIdExisting(projectId);
        //then
        verify(projectRepo).findAll();
        assertTrue(returnTrue);
    }

    @Test
    void isProjectNotExistingReturnFalse() {
        //given
        String wrongProjectId = "123";
        //when
        when(projectRepo.findAll()).thenReturn(List.of());
        boolean returnFalse = projectService.isIdExisting(wrongProjectId);
        //then
        verify(projectRepo).findAll();
        assertFalse(returnFalse);
    }

}
