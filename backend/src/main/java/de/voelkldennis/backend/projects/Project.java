package de.voelkldennis.backend.projects;

import org.springframework.data.annotation.Id;

public record Project(@Id String projectId,
                      String projectName,
                      String shortDescription,
                      Boolean projectVisibility) {
}

