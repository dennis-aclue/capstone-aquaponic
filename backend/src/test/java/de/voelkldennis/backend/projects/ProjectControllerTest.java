package de.voelkldennis.backend.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DirtiesContext
    @Test
    void addNewProjectReturnIsOk() throws Exception {
        //given & when
        String content = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "aquaponic_test_name",
                                    "description": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Project project = objectMapper.readValue(content, Project.class);

        //then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("""
//                    [{
//                        "id": "<id>",
//                        "name": "aquaponic_test_name",
//                        "description": "aquaponic_test_description"
//                    }]
//                    """.replace("<id>", project.id().toString())));

    }
}