package softuni.restaurant.config;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationBeanConfigurationTest {

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String pass = "test";
        String encode = passwordEncoder.encode(pass);

        boolean matches = passwordEncoder.matches(pass, encode);

        assertTrue(matches);
    }

    @Test
    void modelMapper() {

    }



    @Test
    void cloudinary() {
    }

    @Test
    void emailTemplate() {
    }

    @Test
    void localeResolver() {
    }

    @Test
    void localeChangeInterceptor() {
    }

    @Test
    void messageSource() {
    }
}