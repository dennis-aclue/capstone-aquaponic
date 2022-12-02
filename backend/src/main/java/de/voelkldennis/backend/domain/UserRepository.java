package de.voelkldennis.backend.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository("users")
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

}