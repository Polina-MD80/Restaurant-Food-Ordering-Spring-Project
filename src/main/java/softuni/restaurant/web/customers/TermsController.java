package softuni.restaurant.web.customers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {
    @GetMapping("terms-and-conditions")
    public String terms(){
        return "terms-and-conditions";
    }
}
