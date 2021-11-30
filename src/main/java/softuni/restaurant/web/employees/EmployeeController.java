package softuni.restaurant.web.employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    @GetMapping("/terminal")
    public String employee() {
       return "terminal";
    }

}
