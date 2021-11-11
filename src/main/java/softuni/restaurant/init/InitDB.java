package softuni.restaurant.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.model.entity.AllergenEntity;

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

        //allergenService.allAllergensOrderedByName().forEach(allergenViewModel -> System.out.printf("%d %s", allergenViewModel.getId(), allergenViewModel.getName()));

        AllergenEntity milk = allergenService.findByName("MILK");
        System.out.printf("%s %s", milk.getName(), milk.getImageUrl());
    }
}
