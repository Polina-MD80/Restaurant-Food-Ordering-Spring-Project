package softuni.restaurant.web.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsersTerminalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = initUser();
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {

        userRepository.delete(testUser);
    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void usersPageOpens() throws Exception {
        mockMvc
                .perform(get("/terminal/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-terminal"))
                .andExpect(model().attributeExists("allUsers"));
    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void testSaveNewUserCorrect() throws Exception {

        mockMvc
                .perform(post("/terminal/save-user")
                        .param("username", "pesho")
                        .param("email", "pesho@pesho.com")
                        .param("password", "pesho")
                        .with(csrf())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/terminal/users"));

        Optional<UserEntity> pesho = userRepository.findByUsername("pesho");
        assertTrue(pesho.isPresent());
        assertTrue(pesho.get().getId()>0);
        assertEquals("pesho@pesho.com", pesho.get().getEmail());
        userRepository.delete(pesho.get());
    }


    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void testSaveUserExistingUserName() throws Exception {
//TODO how to check that user with the same name exists from the js call rest controller

//        mockMvc
//                .perform(post("/terminal/save-user","terminal/check-username")
//                        .param("username", "test")
//                        .param("email", "pesho@pesho.com")
//                        .param("password", "pesho")
//                        .with(csrf())
//
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/terminal/save-user"));

    }



    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.ADMIN);
    }

}
