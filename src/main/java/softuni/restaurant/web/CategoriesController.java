package softuni.restaurant.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;

@Controller
@RequestMapping("categories")
public class CategoriesController {
    private final CategoryService categoryService;
    private final ItemService itemService;

    public CategoriesController(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("cat/{name}")
    public String gerItemsByCategory(@PathVariable String name, Model model){
        model.addAttribute("itemsByType", itemService.getAllByCategoryName(name));
        return "foods";
    }
}
