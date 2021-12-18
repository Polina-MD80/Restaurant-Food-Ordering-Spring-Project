package softuni.restaurant.web.customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.repository.UserRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = initTestUser();
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(testUser);

    }

    @Test
    void loginPageOpens() throws Exception {
        mockMvc
                .perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginCorrectUser() throws Exception {
        mockMvc
                .perform(post("/users/login")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void testLoginIncorrectUserPass() throws Exception {
        mockMvc
                .perform(post("/users/login")
                        .param("username", "test")
                        .param("password", "wrong")
                        .with(csrf()))
                .andExpect(forwardedUrl("/users/login-error"));

    }

    @Test
    void testLoginIncorrectUsername() throws Exception {
        mockMvc
                .perform(post("/users/login")
                        .param("username", "wrong")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(forwardedUrl("/users/login-error"));

    }


    private UserEntity initTestUser() {
        return userRepository.save(new UserEntity()
                .setUsername("test")
                .setEmail("test@test.com")
                .setPassword(passwordEncoder.encode("test"))
                .setRole(RoleEnum.ADMIN));
    }

}