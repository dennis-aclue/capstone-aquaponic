package de.voelkldennis.backend.projects;

import de.voelkldennis.backend.ProjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectUtils projectUtils;
    private final ProjectRepo projectRepo;

    public Project addNewProject(NewProjectDTO newProjectDTO) {
        String projectId = projectUtils.generateUUID();
        Project saveProject = new Project(projectId, newProjectDTO.userId(), newProjectDTO.username(), newProjectDTO.projectName(), newProjectDTO.shortDescription(), newProjectDTO.projectVisibility());
        return projectRepo.save(saveProject);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Optional<Project> getProjectWithId(String projectId) {
        return projectRepo.findByProjectId(projectId);
    }


    public Project deleteProjectWithId(String projectId) {
        Project deletedProject = projectRepo.findByProjectId(projectId).orElseThrow(
                () -> new NoSuchElementException("Project does not exist"));
        projectRepo.deleteByProjectId(projectId);
        return deletedProject;
    }

    public boolean isIdExisting(String projectId) {
        List<Project> projects = projectRepo.findAll();
        for (Project project : projects) {
            if (project.projectId().equals(projectId)) {
                return true;
            }
        }
        return false;
    }

    public Project updateProjectWithId(String projectId, NewProjectDTO newProjectDTO) {
        Project updatedProject = new Project(
                projectId,
                newProjectDTO.userId(),
                newProjectDTO.username(),
                newProjectDTO.projectName(),
                newProjectDTO.shortDescription(),
                newProjectDTO.projectVisibility()
        );
        return projectRepo.save(updatedProject);
    }

    public List<Project> getAllVisibleProjects() {
        return projectRepo.findAll().stream()
                .filter(Project::projectVisibility)
                .toList();
    }

    public List<Project> getUserProjects(String userId) {
        return projectRepo.findAll().stream()
                .filter(project -> project.userId().equals(userId))
                .toList();
    }
}
