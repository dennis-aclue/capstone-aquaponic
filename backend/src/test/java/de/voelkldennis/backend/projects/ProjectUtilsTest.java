package de.voelkldennis.backend.projects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProjectUtilsTest {

    @Test
    void generateUUIDAndGetUUID() {
        //given
        ProjectUtils projectUtils = new ProjectUtils();
        //when
        UUID actual = projectUtils.generateUUID();
        //then
        assertNotNull(actual);
    }
}
