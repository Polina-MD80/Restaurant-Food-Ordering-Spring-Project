package softuni.restaurant.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foods")
public class FoodsController {
    @GetMapping()
    public String foods(){
        return "foods";
    }

    @GetMapping("/{category}")
    public String findFoodByCategory(@PathVariable String category){

        return "foods";

    }
}
