package de.voelkldennis.backend.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DirtiesContext
    @Test
    //@WithMockUser(username = "test_username")
    //@WithMockUser(username = "user", roles = "ROLE_USER", password = "test_password")
    @WithMockUser
    void addNewProjectReturnIsOk() throws Exception {

        //User user = new User();
        //user.setUsername("user");
        //user.setPassword("test_password");

/*
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName":"Christian",
                                    "lastName":"Chris",
                                    "username":"user",
                                    "email":"chris@googlemail.com"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_test_name",
                                    "shortDescription": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().is(201));*/

    }

    @DirtiesContext
    @Test
    void getAllProjectsAndReturnOk() throws Exception {

    }

    @DirtiesContext
    @Test
    void getProjectWithIdAndReturnOk() throws Exception {

    }

    @Test
    @DirtiesContext
    void deleteProjectWithIdAndReturnNoContent() throws Exception {


    }

    @Test
    @DirtiesContext
    void deleteProjectWithNotExistingIdAndReturnNoContent() throws Exception {

    }

    @Test
    @DirtiesContext
    void updateProjectWithExistingIdAndReturnUpdatedProject() throws Exception {


    }

    @Test
    @DirtiesContext
    void updateProjectWithNotExistingIdAndReturn404() throws Exception {

    }


    @Test
    void getAllVisibleProjects() throws Exception {
        //given&when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/freeGallery"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

