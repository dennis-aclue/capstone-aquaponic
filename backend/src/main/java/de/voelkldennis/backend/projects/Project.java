package de.voelkldennis.backend.projects;

public record Project(String projectId,
                      String projectName,
                      String shortDescription,
                      Boolean projectVisibility)
{
}

