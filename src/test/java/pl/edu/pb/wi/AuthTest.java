package pl.edu.pb.wi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void isRegisterAndLoginWorking() throws Exception {
        //given
        String sampleFirstName = "sampleName";
        String sampleLastName = "sampleName";
        String sampleUsername = "sampleName";
        String sampleEmail = "test@example.com";
        String samplePassword = "password123";

        String registerJson = objectMapper.writeValueAsString(Map.of(
                "firstName", sampleFirstName,
                "lastName", sampleLastName,
                "username", sampleUsername,
                "password", samplePassword,
                "email", sampleEmail));

        //when
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        //then
        Optional<User> optionalUser = userRepository.findUserByUsername(sampleUsername);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        assertEquals(sampleFirstName, user.getFirstName());
        assertEquals(sampleLastName, user.getLastName());
        assertEquals(sampleUsername, user.getUsername());
        assertTrue(bCryptPasswordEncoder.matches(samplePassword, user.getPassword()));
        assertEquals(sampleEmail, user.getEmail());
        assertEquals(Role.RoleType.USER.getName(), user.getRole().getName());

        //given
        String loginJson = objectMapper.writeValueAsString(Map.of(
                "username", sampleUsername,
                "password", samplePassword));

        //when, then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
    }

}
