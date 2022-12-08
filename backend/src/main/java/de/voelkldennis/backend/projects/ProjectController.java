package de.voelkldennis.backend.projects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/", "/api/projects"})
public class ProjectController {

    private final ProjectService projectService;

    Base64.Decoder decoder = Base64.getUrlDecoder();

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

    /*@GetMapping("/userProjectOverview/{")
    List<Project> getUserProjects(@RequestHeader HttpHeaders headers){
        System.out.println(headers);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());


        //String header = new String(decoder.decode(chunks[0]));
        //String payload = new String(decoder.decode(chunks[1]));

        //System.out.println(header);
        //System.out.println(payload);
        //String username = payload.split(":")[1].split(",")[0].replace("\"", "");
        //System.out.println(username);

        return null;
    }*/

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
