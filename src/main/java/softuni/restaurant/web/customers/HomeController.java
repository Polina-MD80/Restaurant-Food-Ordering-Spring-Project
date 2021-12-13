package softuni.restaurant.web.customers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.restaurant.service.ItemService;

@Controller
public class HomeController {
    private final ItemService itemService;

    public HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("allItems",itemService.getAllItems());
        return "index";
    }
}
