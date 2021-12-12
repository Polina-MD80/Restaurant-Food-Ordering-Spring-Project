package softuni.restaurant.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.restaurant.service.OrderService;

@Controller
public class OrderController {


    @GetMapping("/order")
    public String order(){

        return "order";
    }
}
