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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.ADMIN);
    }

}
