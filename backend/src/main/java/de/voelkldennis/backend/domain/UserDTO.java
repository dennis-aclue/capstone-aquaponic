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
        String password,
        String[] projects

) {
        @Override
        public boolean equals(Object obj) {
                return false;
        }

        @Override
        public int hashCode() {
                return 0;
        }

        @Override
        public String toString() {
                return "null";
        }
}
