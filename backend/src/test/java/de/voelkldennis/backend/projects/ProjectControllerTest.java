package de.voelkldennis.backend.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.voelkldennis.backend.SecurityConfiguration;
import de.voelkldennis.backend.domain.User;
import de.voelkldennis.backend.domain.UserPrincipal;
import de.voelkldennis.backend.jwt.filter.JwtAccessDeniedHandler;
import de.voelkldennis.backend.jwt.filter.JwtAuthenticationEntryPoint;
import de.voelkldennis.backend.jwt.filter.JwtAuthorizationFilter;
import de.voelkldennis.backend.jwt.utility.JWTTokenProvider;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class ProjectControllerTest {

    private UserPrincipal userPrincipal;
    private UserDetailsService userDetailsService;
    private User user;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Mock
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Mock
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    @Mock
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Mock
    private JWTTokenProvider jwtTokenProvider;

    private SecurityConfiguration securityConfiguration;

/*    @BeforeEach
    public void init() {
        String[] authorities = {"user:read"};
        this.user = new User();
        this.user.setUsername("dennisvoelkl");
        this.user.setPassword(bCryptPasswordEncoder.encode("MROO74RGPE"));
        this.user.setRole("ROLE_USER");
        this.user.setAuthorities(authorities);
    }*/

    @Autowired
    protected WebApplicationContext wac;


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void applySecurity() {
        this.mockMvc = webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @DirtiesContext
    @Test
    //@WithMockUser(username = "dennisvoelkl", password = "MROO74RGPE", roles = "ROLE_USER", authorities = "ROLE_USER")
    @WithMockUser
    void addNewProjectReturnIsOk() throws Exception {

        //System.out.println(this.user.getUsername());
        //System.out.println(this.user.getPassword());


        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username": "test",
                                "password": "test"}
                                """))
                .andExpect(status().isOk());
//
//        //given & when
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                    "projectName": "aquaponic_test_name",
//                                    "shortDescription": "aquaponic_test_description"
//                                }
//                                """))
//                .andExpect(status().is(201));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", roles = "read", authorities = {"USER_AUTHORITIES"})
    //@WithMockUser
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

