package softuni.restaurant.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.restaurant.service.*;

@Component
public class InitDB  implements CommandLineRunner {
    private final AllergenService allergenService;
    private final UserService userService;
    private final ProductService productService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final PictureService pictureService;

    public InitDB(AllergenService allergenService, UserService userService, ProductService productService, ItemService itemService, CategoryService categoryService, PictureService pictureService) {
        this.allergenService = allergenService;
        this.userService = userService;
        this.productService = productService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.pictureService = pictureService;
    }

    @Override
    public void run(String... args) throws Exception {
        allergenService.initAllergens();
        userService.initUsers();
        productService.initializeProducts();
        pictureService.initializePictures();
        categoryService.initializeCategories();
        itemService.initializeItems();

    }
}
