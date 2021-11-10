package softuni.restaurant.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.restaurant.Service.AllergenService;
import softuni.restaurant.Service.UserService;

@Component
public class InitDB implements CommandLineRunner {
    private final AllergenService allergenService;
    private final UserService userService;

    public InitDB(AllergenService allergensService, UserService userService) {
        this.allergenService = allergensService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        allergenService.initAllergens();
        userService.initUsers();
    }
}
