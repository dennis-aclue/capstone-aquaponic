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
        String name = "aquaponic_test_name";
        String description = "aquaponic_test_description";
        Project projectWithId = new Project(uuid, name, description);
        NewProjectDTO newProjectDTO = new NewProjectDTO(name, description);
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