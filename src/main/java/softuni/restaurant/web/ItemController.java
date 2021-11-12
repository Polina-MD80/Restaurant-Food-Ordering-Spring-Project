package softuni.restaurant.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.restaurant.model.binding.ItemAddBindingModel;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.ProductService;

@Controller
@RequestMapping("items")
public class ItemController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final ProductService productService;


    public ItemController(CategoryService categoryService, ModelMapper modelMapper, ItemService itemService, ProductService productService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.productService = productService;
    }

    @ModelAttribute
    public ItemAddBindingModel addBindingModel(){
        return new ItemAddBindingModel();
    }

    @GetMapping("add")
    public String products(Model model) {
     model.addAttribute("categoriesByName", categoryService.getAllCategoryNames());
     model.addAttribute("allItems", itemService.getAllItems());

        return "item-add";
    }
}
