package softuni.restaurant.web.customers;

import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.*;
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
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(testUser);

    }

    @Test
    void userModel() {
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
                .setEmail("test@test.bg")
                .setPassword(passwordEncoder.encode("test"))
                .setRole(RoleEnum.ADMIN));
    }
}