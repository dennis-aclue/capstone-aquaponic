package de.voelkldennis.backend.projects;

public record NewProjectDTO(
        String userId,
        String username,
        String projectName,
        String shortDescription,
        Boolean projectVisibility
) {

}
