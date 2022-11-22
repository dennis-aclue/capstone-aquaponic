package de.voelkldennis.backend.projects;

public record NewProjectDTO(String projectName,
                            String shortDescription,
                            Boolean projectVisibility) {

    public Boolean projectVisibility(boolean projectVisibility) {
        return projectVisibility;
    }
}
