package de.voelkldennis.backend.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepo")
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

}