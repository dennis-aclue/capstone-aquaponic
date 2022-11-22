package de.voelkldennis.backend.projects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    ProjectUtils projectUtils = mock(ProjectUtils.class);
    ProjectRepo projectRepo = mock(ProjectRepo.class);
    ProjectService projectService = new ProjectService(projectUtils, projectRepo);

    @Test
    void addNewProject() {
        //given
        UUID uuid = UUID.fromString("add4ee6b-a702-4553-a0b0-2c07724b5b8b");
        String projectName = "aquaponic_test_name";
        String shortDescription = "aquaponic_test_description";
        Project projectWithId = new Project(uuid, projectName, shortDescription);
        NewProjectDTO newProjectDTO = new NewProjectDTO(projectName, shortDescription);
        //when
        when(projectUtils.generateUUID()).thenReturn(uuid);
        when(projectRepo.save(projectWithId)).thenReturn(projectWithId);
        Project actual = projectService.addNewProject(newProjectDTO);
        //then
        verify(projectUtils).generateUUID();
        verify(projectRepo).save(projectWithId);
        assertEquals(projectWithId, actual);
    }
}
