package de.voelkldennis.backend.projects;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    Project addNewProject(@RequestBody NewProjectDTO newProjectDTO ){
        return projectService.addNewProject(newProjectDTO);
    }

    @GetMapping
    List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

}
