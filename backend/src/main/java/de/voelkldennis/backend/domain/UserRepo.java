package de.voelkldennis.backend.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepo")
public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);

    void deleteByUsername(String username);

}