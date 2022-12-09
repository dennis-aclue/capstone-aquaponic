package de.voelkldennis.backend.domain;

import javax.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String username,
        @NotBlank
        String email,
        String password

) {

}
