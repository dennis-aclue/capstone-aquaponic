package de.voelkldennis.backend.projects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DirtiesContext
    @Test
    void addNewProjectReturnIsOk() throws Exception {
        //given & when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_test_name",
                                    "shortDescription": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().is(201));
    }

    @DirtiesContext
    @Test
    void getAllProjectsAndReturnOk() throws Exception {
        //given&when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @DirtiesContext
    @Test
    void getProjectWithIdAndReturnOk() throws Exception {
        //given
        String projectId = "add4ee6b-a702-4553-a0b0-2c07724b5b8b";
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + projectId))
                .andExpect(status().is(200));
    }
}
