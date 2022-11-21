package de.voelkldennis.backend.projects;

import java.util.UUID;

public record Project(UUID id,
                      String name,
                      String description)
{
}
