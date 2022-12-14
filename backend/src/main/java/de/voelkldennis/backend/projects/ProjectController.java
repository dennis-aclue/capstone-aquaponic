package de.voelkldennis.backend.projects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/api/projects"})
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/addProject")
    @ResponseStatus(code = HttpStatus.CREATED)
    Project addNewProject(@RequestBody NewProjectDTO newProjectDTO) {
        return projectService.addNewProject(newProjectDTO);
    }

    @GetMapping("/projectOverview")
    List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/userProjectOverview/{userId}")
    List<Project> getUserProjects(@PathVariable String userId) {
        return projectService.getUserProjects(userId);
    }

    @GetMapping("/projectCard/{projectId}")
    Optional<Project> getProjectWithId(@PathVariable String projectId) {
        return projectService.getProjectWithId(projectId);
    }

    @DeleteMapping("/{projectId}")
    public Project deleteProjectWithId(@PathVariable String projectId) {
        if ((projectService.isIdExisting(projectId))) {
            return projectService.deleteProjectWithId(projectId);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project " + projectId + " not found");
    }

    @PutMapping("/update/{projectId}")
    public Project updateProjectWithId(@PathVariable String projectId, @RequestBody NewProjectDTO newProjectDTO) {
        if ((projectService.isIdExisting(projectId))) {
            return projectService.updateProjectWithId(projectId, newProjectDTO);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project " + projectId + " not found");
    }

    @GetMapping("/freeGallery")
    List<Project> getAllVisibleProjects() {
        return projectService.getAllVisibleProjects();
    }

}
