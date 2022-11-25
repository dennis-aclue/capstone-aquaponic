package de.voelkldennis.backend.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectUtils projectUtils;
    private final ProjectRepo projectRepo;

    public Project addNewProject(NewProjectDTO newProjectDTO) {
        String projectId = projectUtils.generateUUID();
        Project saveProject = new Project(projectId, newProjectDTO.projectName(), newProjectDTO.shortDescription(), newProjectDTO.projectVisibility(false));
        return projectRepo.save(saveProject);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Optional<Project> getProjectWithId(String projectId) {
        return projectRepo.findByProjectId(projectId);
    }


    public Project deleteProjectWithId(String projectId) {
        projectRepo.deleteByProjectId(projectId);
        return null;
    }

    public boolean isIdExisting(String projectId) {
        List<Project> projects = projectRepo.findAll();
        for (Project project : projects) {
            if (!project.projectId().equals(projectId)) {
                return false;
            }
        }
        return true;
    }
}

