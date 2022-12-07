package de.voelkldennis.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectUtilsTest {

    @Test
    void generateUUIDAndGetUUID() {
        //given
        ProjectUtils projectUtils = new ProjectUtils();
        //when
        String actual = projectUtils.generateUUID();
        //then
        assertNotNull(actual);
    }
}
