package de.voelkldennis.backend.projects;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class ProjectUtils {

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

}
