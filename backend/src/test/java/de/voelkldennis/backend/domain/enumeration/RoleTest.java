package de.voelkldennis.backend.domain.enumeration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class RoleTest {

    @Test
    void getAuthorities() {
        assertNotNull(Role.ROLE_USER.getAuthorities());
    }

    @Test
    void values() {
        assertNotNull(Role.values());
    }

    @Test
    @DirtiesContext
    void valueOf() {
        for (Role role : Role.values()) {
            assertNotNull(role);
        }
    }
}
