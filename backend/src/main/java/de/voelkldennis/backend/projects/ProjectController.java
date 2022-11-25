package de.voelkldennis.backend.projects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    Project addNewProject(@RequestBody NewProjectDTO newProjectDTO) {
        return projectService.addNewProject(newProjectDTO);
    }

    @GetMapping
    List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projectCard/{projectId}")
    Optional<Project> getProjectWithId(@PathVariable String projectId) {
        return projectService.getProjectWithId(projectId);
    }

    @DeleteMapping("/{projectId}")
    void deleteProjectWithId(@PathVariable String projectId) {
        if ((projectService.isIdExisting(projectId))) {
            projectService.deleteProjectWithId(projectId);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with Id " + projectId + " not found");
    }

}
