package softuni.restaurant.web.employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsController {
    @GetMapping("terminal/stats")
    public String stats(){
        return "stats";
    }
}
