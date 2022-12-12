package de.voelkldennis.backend.projects;

import de.voelkldennis.backend.ProjectUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectUtilsTest {

    @Test
    void generateUUIDAndGetUUID() {
        //given
        de.voelkldennis.backend.ProjectUtils projectUtils = new ProjectUtils();
        //when
        String actual = projectUtils.generateUUID();
        //then
        assertNotNull(actual);
    }
}
