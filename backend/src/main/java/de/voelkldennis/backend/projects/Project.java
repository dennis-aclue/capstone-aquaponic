package de.voelkldennis.backend.projects;

import java.util.UUID;

public record Project(UUID projectId,
                      String projectName,
                      String shortDescription)
{
}
