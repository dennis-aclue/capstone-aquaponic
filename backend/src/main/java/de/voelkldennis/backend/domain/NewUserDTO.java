package de.voelkldennis.backend.domain;

public record NewUserDTO(String firstName,
                         String lastName,
                         String email,
                         String username) {
}
