package de.voelkldennis.backend.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
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

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithUserDetails()
    void getAllProjectsAndReturnOk() throws Exception {
        //given&when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @DirtiesContext
    @Test
    void getProjectWithIdAndReturnOk() throws Exception {

        String content = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_test_name",
                                    "shortDescription": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().is(201))
                .andReturn().getResponse().getContentAsString();

        Project project = objectMapper.readValue(content, Project.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + project.projectId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                            "projectId": "<ID>",
                            "projectName": "aquaponic_test_name",
                            "shortDescription": "aquaponic_test_description",
                            "projectVisibility": false
                        }
                        """.replace("<ID>", project.projectId())));
    }

    @Test
    @DirtiesContext
    void deleteProjectWithIdAndReturnNoContent() throws Exception {

        String content = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_test_name",
                                    "shortDescription": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Project project = objectMapper.readValue(content, Project.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/" + project.projectId()))
                .andExpect(status().is(200));

    }

    @Test
    @DirtiesContext
    void deleteProjectWithNotExistingIdAndReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/123"))
                .andExpect(status().is(404));
    }

    @Test
    @DirtiesContext
    void updateProjectWithExistingIdAndReturnUpdatedProject() throws Exception {

        String content = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_test_name",
                                    "shortDescription": "aquaponic_test_description"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Project newUpdatedProject = objectMapper.readValue(content, Project.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + newUpdatedProject.projectId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                            "projectId": "<ID>",
                            "projectName": "aquaponic_test_name",
                            "shortDescription": "aquaponic_test_description",
                            "projectVisibility": false
                        }
                        """.replace("<ID>", newUpdatedProject.projectId())));


        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/update/" + newUpdatedProject.projectId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_project",
                                    "shortDescription": "aquaponic_project_description",
                                    "projectVisibility": true
                                }
                                """))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + newUpdatedProject.projectId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                                {
                                    "projectId": "<ID>",
                                    "projectName": "aquaponic_project",
                                    "shortDescription": "aquaponic_project_description",
                                    "projectVisibility": true
                                }
                        """.replace("<ID>", newUpdatedProject.projectId())));

    }

    @Test
    @DirtiesContext
    void updateProjectWithNotExistingIdAndReturn404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/update/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "projectName": "aquaponic_project",
                                    "shortDescription": "aquaponic_project_description",
                                    "projectVisibility": true
                                }
                                """))
                .andExpect(status().is(404));
    }


    @Test
    void getAllVisibleProjects() throws Exception {
        //given&when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/freeGallery"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

