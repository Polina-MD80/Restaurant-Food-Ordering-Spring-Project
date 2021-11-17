package softuni.restaurant.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.restaurant.repository.CategoryRepository;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.UserService;

@Component
public class InitDB implements CommandLineRunner {
    private final AllergenService allergenService;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public InitDB(AllergenService allergensService, UserService userService, ItemService itemService, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.allergenService = allergensService;
        this.userService = userService;
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        allergenService.initAllergens();
        userService.initUsers();

        //allergenService.allAllergensOrderedByName().forEach(allergenViewModel -> System.out.printf("%d %s", allergenViewModel.getId(), allergenViewModel.getName()));

//        AllergenEntity milk = allergenService.findByName("MILK");
//        System.out.printf("%s %s", milk.getName(), milk.getImageUrl());
//
//
//        itemRepository.allFoods().forEach(System.out::println);
//        itemService.getAllFoods().forEach(System.out::println);

//      System.out.println(categoryRepository.findByName("HOHO").get().getItems());
    }
}
