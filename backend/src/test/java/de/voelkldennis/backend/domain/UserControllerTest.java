package de.voelkldennis.backend.domain;

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

import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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
        user.setId("1");
        user.setUsername("user");
        user.setPassword(password);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("test@test.de");
        user.setRole("ROLE_SUPER_ADMIN");
        user.setAuthorities(userAuthority);
        user.setNotLocked(true);
        user.setActive(true);
        user.setLastLoginDate(new Date());
        user.setLastLoginDateDisplay(new Date());
        user.setJoinDate(new Date());
        userRepository.save(user);

    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "user")
    void loginWithRightCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void loginWithWrongUserNameCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "wrong_user",
                            "password": "password"
                        }
                        """).with(csrf())).andExpect(status().is(400));
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void loginWithWrongPasswordCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user",
                            "password": "wrong_password"
                        }
                        """).with(csrf())).andExpect(status().is(500));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "userDTO")
    void registerReturnOK() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {
                                "firstName": "dto_firstName",
                                "lastName": "dto_lastName",
                                "username": "userDTO",
                                "email": "dto@test.de",
                                "password": "test_password"
                             }
                            """
                    ).with(csrf())).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "userDTO")
    void registerWithAlreadyExistingUsername() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {
                                "firstName": "dto_firstName",
                                "lastName": "dto_lastName",
                                "username": "user",
                                "email": "dto@test.de",
                                "password": "test_password"
                             }
                            """
                    ).with(csrf())).andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "userDTO")
    void registerWithAlreadyExistingEmail() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {
                                "firstName": "dto_firstName",
                                "lastName": "dto_lastName",
                                "username": "userDTO",
                                "email": "test@test.de",
                                "password": "test_password"
                             }
                            """
                    ).with(csrf())).andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "user", roles = {"SUPER_ADMIN"})
    void deleteUser() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk());

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void resetPassword() throws Exception {

        String email = "test@test.de";

        mockMvc.perform(MockMvcRequestBuilders.post("/user/resetPassword/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "email": "test@test.de"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk());

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void getUserData() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserData/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .with(csrf()))
                .andExpect(status().isOk());

    }


    @DirtiesContext
    @Test
    @WithMockUser
    void updateUserWithCorrectId() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/updateUser/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                    "firstName": "dto_firstName",
                                    "lastName": "dto_lastName",
                                    "username": "user",
                                    "email": "test@test.de"
                                  }
                                """).with(csrf()))
                .andExpect(status().isOk());

    }

    @DirtiesContext
    @Test
    @WithMockUser
    void updateUserWithNotPresentId() throws Exception {

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "test_password"
                                }
                                """).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse().getHeaders("JwtToken").get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/updateUser/" + "100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("JwtToken", response)
                        .content("""
                                {
                                "firstName": "dto_firstName",
                                "lastName": "dto_lastName",
                                "username": "userDTO",
                                "email": "dto@test.de"
                                  }
                                """).with(csrf()))
                .andExpect(status().is(400));

    }

}