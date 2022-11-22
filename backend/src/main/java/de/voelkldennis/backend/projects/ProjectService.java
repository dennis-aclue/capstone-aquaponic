package de.voelkldennis.backend.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectUtils projectUtils;
    private final ProjectRepo projectRepo;

    public Project addNewProject( NewProjectDTO newProjectDTO) {
        UUID projectId = projectUtils.generateUUID();
        Project saveProject = new Project(projectId, newProjectDTO.projectName(), newProjectDTO.shortDescription());
        return projectRepo.save(saveProject);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }
}
