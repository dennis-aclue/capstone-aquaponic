package de.voelkldennis.backend.projects;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepo extends MongoRepository <Project, UUID> {
}
