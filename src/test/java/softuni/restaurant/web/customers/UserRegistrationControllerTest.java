package softuni.restaurant.web.customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.repository.UserRepository;


import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationControllerTest {
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
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(testUser);

    }

    @Test
    void registerPageOpens() throws Exception {
        mockMvc
                .perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegisterUserCorrect() throws Exception {

        mockMvc
                .perform(post("/users/register")
                        .param("username", "pesho")
                        .param("email", "pesho@pesho.com")
                        .param("password", "pesho")
                        .param("confirmPassword", "pesho")
                        .with(csrf())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Optional<UserEntity> pesho = userRepository.findByUsername("pesho");
        assertTrue(pesho.isPresent());
        assertTrue(pesho.get().getId()>0);
        assertEquals("pesho@pesho.com", pesho.get().getEmail());
        userRepository.delete(pesho.get());
    }

    @Test
    void testRegisterUserIncorrectPassConf() throws Exception {

        mockMvc
                .perform(post("/users/register")
                        .param("username", "pesho")
                        .param("email", "pesho@pesho.com")
                        .param("password", "pesho")
                        .param("confirmPassword", "pesho7777")
                        .with(csrf())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));

    }

    @Test
    void testRegisterUserIncorrectExisting() throws Exception {

        mockMvc
                .perform(post("/users/register")
                        .param("username", "test")
                        .param("email", "pesho@pesho.com")
                        .param("password", "pesho")
                        .param("confirmPassword", "pesho")
                        .with(csrf())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));

    }
    @Test
    void registerUser() {
    }

    @Test
    void register() {
    }

    private UserEntity initTestUser() {
        return userRepository.save(new UserEntity()
                .setUsername("test")
                .setEmail("test@test.com")
                .setPassword(passwordEncoder.encode("test"))
                .setRole(RoleEnum.ADMIN));
    }
}
