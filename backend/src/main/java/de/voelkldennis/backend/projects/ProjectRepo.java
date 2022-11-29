package de.voelkldennis.backend.projects;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepo extends MongoRepository<Project, String> {
    Optional<Project> findByProjectId(String projectId);

    void deleteByProjectId(String projectId);

}
