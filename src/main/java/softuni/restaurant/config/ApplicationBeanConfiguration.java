package softuni.restaurant.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Map;

@Configuration
public class ApplicationBeanConfiguration {
    private final CloudinaryConfig cloudinaryConfig;

    public ApplicationBeanConfiguration(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudinaryConfig.getCloudName(),
                        "api_key", cloudinaryConfig.getApiKey(),
                        "api_secret", cloudinaryConfig.getApiSecret()
                )
        );
    }

    @Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("user@gmail.com");
        message.setFrom("admin@gmail.com");
        message.setSubject("Important email");
        message.setText("FATAL - Application crash. Save your job !!");
        return message;
    }
}
