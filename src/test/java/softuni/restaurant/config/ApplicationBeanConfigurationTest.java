package softuni.restaurant.config;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationBeanConfigurationTest {

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String pass = "Hello";
        String encode = passwordEncoder.encode(pass);

        boolean matches = passwordEncoder.matches(pass, encode);

        assertTrue(matches);
    }
}