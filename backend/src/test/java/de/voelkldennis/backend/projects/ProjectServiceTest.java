package de.voelkldennis.backend.projects;

import de.voelkldennis.backend.ProjectUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        String username = "aquaponic_test_username";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;
        Project projectWithId = new Project(projectId, "userId", username, projectName, shortDescription, projectVisibility);
        NewProjectDTO newProjectDTO = new NewProjectDTO("userId", username, projectName, shortDescription, projectVisibility);
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
        Project projectWithId = new Project(projectId, "userId", "username", projectName, shortDescription, projectVisibility);
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
        Project projectWithId = new Project(projectId, "userId", "username", projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.of(projectWithId));
        Project actual = projectService.deleteProjectWithId(projectId);
        //then
        verify(projectRepo).deleteByProjectId(projectWithId.projectId());
        verify(projectRepo).findByProjectId(projectId);
        assertEquals(projectWithId, actual);
    }

    @Test
    void deleteProjectWithNoneExistingId() {
        //given
        String projectId = "123";
        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.empty());
        //when
        String message = null;
        try {
            projectService.deleteProjectWithId(projectId);
        } catch (NoSuchElementException e) {
            message = e.getMessage();
        }
        //then
        assertEquals("Project does not exist", message);
    }


    @Test
    void isProjectIdExistingReturnTrue() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;

        Project projectWithId = new Project(projectId, "userId", "username", projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findAll()).thenReturn(List.of(projectWithId));
        assertTrue(projectService.isIdExisting(projectId));

    }

    @Test
    void isProjectIdNotExistingReturnFalse() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        String fakeProjectId = "123";
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Boolean projectVisibility = false;

        Project projectWithId = new Project(projectId, "userId", "username", projectName, shortDescription, projectVisibility);
        //when
        when(projectRepo.findAll()).thenReturn(List.of(projectWithId));
        assertFalse(projectService.isIdExisting(fakeProjectId));

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

    @Test
    void updateProjectWithIdAndReturnUpdatedProject() {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        Project projectWithId = new Project(projectId,
                "user_id",
                "username",
                "aquaponic_test_name",
                "aquaponic_test_description",
                false);
        NewProjectDTO newProjectDTO = new NewProjectDTO("user_id",
                "username",
                "updated_name",
                "updated_description",
                true);
        Project updatedProject = new Project(projectId,
                "user_id",
                "username",
                "updated_name",
                "updated_description",
                true);
        //when
        when(projectRepo.save(updatedProject)).thenReturn(updatedProject);
        Project actual = projectService.updateProjectWithId(projectId, newProjectDTO);
        //then
        verify(projectRepo).save(updatedProject);
        assertNotSame(projectWithId, actual);
    }

    @Test
    void getAllVisibleProjects() {
        //given
        List<Project> testProject = new ArrayList<>();
        //when
        when(projectRepo.findAll()).thenReturn(testProject);
        List<Project> actual = projectService.getAllVisibleProjects();
        //then
        assertEquals(testProject, actual);
    }
}
