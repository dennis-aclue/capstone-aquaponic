package de.voelkldennis.backend.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepo projectRepo;

    private final ProjectUtils projectUtils;

    public Project addNewProject( NewProjectDTO newProjectDTO) {
        UUID id = projectUtils.generateUUID();
        Project saveProject = new Project(id, newProjectDTO.name(), newProjectDTO.description());
        return projectRepo.save(saveProject);
    }
}
