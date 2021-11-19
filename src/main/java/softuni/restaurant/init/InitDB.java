package softuni.restaurant.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.restaurant.repository.CategoryRepository;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.*;

@Component
public class InitDB implements CommandLineRunner {
    private final AllergenService allergenService;
    private final UserService userService;
    private final ProductService productService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final PictureService pictureService;

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public InitDB(AllergenService allergensService, UserService userService, ProductService productService, ItemService itemService, CategoryService categoryService, PictureService pictureService, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.allergenService = allergensService;
        this.userService = userService;
        this.productService = productService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.pictureService = pictureService;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        allergenService.initAllergens();
        userService.initUsers();
        productService.initializeProducts();
        pictureService.initializePictures();
        categoryService.initializeCategories();
        itemService.initializeItems();


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
