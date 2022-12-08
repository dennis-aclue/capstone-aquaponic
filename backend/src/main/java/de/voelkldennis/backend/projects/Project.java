package de.voelkldennis.backend.projects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("projects")
public record Project(@Id String projectId,
                      String userId,
                      String username,
                      String projectName,
                      String shortDescription,
                      Boolean projectVisibility
) {
}

