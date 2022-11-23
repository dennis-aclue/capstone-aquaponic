package de.voelkldennis.backend.projects;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class ProjectUtils {

    public String generateUUID() {
        UUID randomId = UUID.randomUUID();
        return randomId.toString();
    }

}
