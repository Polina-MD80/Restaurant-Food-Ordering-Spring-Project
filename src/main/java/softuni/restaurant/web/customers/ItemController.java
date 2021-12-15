package softuni.restaurant.web.customers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.ProductService;

@Controller
@RequestMapping("items")
public class ItemController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final ProductService productService;
    private final PictureService pictureService;


    public ItemController(CategoryService categoryService, ModelMapper modelMapper, ItemService itemService, ProductService productService, PictureService pictureService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.productService = productService;
        this.pictureService = pictureService;
    }

    @GetMapping("foods")
    public String foods(Model model) {
        model.addAttribute("itemsByType", itemService.getAllFoods());
        return "foods";
    }

    @GetMapping("drinks")
    public String drinks(Model model) {
        model.addAttribute("itemsByType", itemService.getAllDrinks());
        return "foods";
    }

    @GetMapping("others")
    public String other(Model model) {
        model.addAttribute("itemsByType", itemService.getAllOther());
        return "foods";
    }

    @GetMapping ("search")
    public String search(@RequestParam ("keyword") String keyword, Model model) {
        model.addAttribute("itemsByType", itemService.getAllByKeyWord(keyword));
        return "foods";
    }

}
