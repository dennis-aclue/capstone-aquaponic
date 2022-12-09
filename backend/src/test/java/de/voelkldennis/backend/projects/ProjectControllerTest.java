package de.voelkldennis.backend.projects;

import de.voelkldennis.backend.domain.User;
import de.voelkldennis.backend.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        String password = passwordEncoder.encode("test_password");
        String[] userAuthority = {"user:read"};
        User user = new User();
        user.setUserId("1");
        user.setUsername("user");
        user.setPassword(password);
        user.setFirstName("Christian");
        user.setLastName("Chris");
        user.setEmail("test@tes.de");
        user.setRole("ROLE_USER");
        user.setAuthorities(userAuthority);
        user.setNotLocked(true);
        user.setActive(true);
        userRepository.save(user);
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void addNewProjectReturnIsOk() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void getAllProjectsAndReturnOk() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectOverview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void getUserProjects() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        String getUserId = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsString();

        String userId = getUserId.split(":")[2].split(",")[0].trim();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/userProjectOverview/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void getProjectWithIdAndReturnOk() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        String getProjectId = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsString();

        String projectId = (getProjectId.split(":")[1].split(",")[0].trim().replace("\"", ""));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                               "projectId": "<ID>",
                               "userId": "1",
                               "username": "user",
                               "projectName": "Test Project",
                               "shortDescription": "Test Project Short Description",
                               "projectVisibility": false
                        }
                        """.replace("<ID>", projectId)));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void deleteProjectWithIdAndReturnNoContent() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        String getProjectId = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsString();

        String projectId = (getProjectId.split(":")[1].split(",")[0].trim().replace("\"", ""));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().is(200));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void deleteProjectWithNotExistingIdAndReturnNoContent() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsString();


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/" + "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().is(500));

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void updateProjectWithExistingIdAndReturnUpdatedProject() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        String getProjectId = mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project",
                                    "shortDescription": "Test Project Short Description"
                                }
                                """).with(csrf()))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsString();

        String projectId = (getProjectId.split(":")[1].split(",")[0].trim().replace("\"", ""));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/update/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "userId": "1",
                                    "username": "user",
                                    "projectName": "Test Project Updated",
                                    "shortDescription": "Test Project Short Description Updated",
                                    "projectVisibility": true
                                }
                                """).with(csrf()))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/projectCard/" + projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "userId": "1",
                            "username": "user",
                            "projectName": "Test Project Updated",
                            "shortDescription": "Test Project Short Description Updated",
                            "projectVisibility": true
                        }
                        """));

    }

    @Test
    @DirtiesContext
    void getAllVisibleProjects() throws Exception {
        //given&when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/freeGallery"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
