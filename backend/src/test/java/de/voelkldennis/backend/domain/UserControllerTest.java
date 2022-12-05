package de.voelkldennis.backend.domain;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    String base64ClientCredentials = new String(Base64.encodeBase64("user:SuperSecret344$$".getBytes()));

    @Test
    @WithMockUser
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .header("Authorization", "Basic " + base64ClientCredentials)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk()).andExpect(content().string("OK"));
    }
}