package softuni.restaurant.web.employees;



import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import softuni.restaurant.model.view.StatsView;
import softuni.restaurant.service.StatsService;

@RestController
@RequestMapping("/terminal/stats")
public class StatsRestController {

    private final StatsService statsService;

    public StatsRestController(StatsService statsService) {
        this.statsService = statsService;
    }



    @GetMapping
    public ResponseEntity<StatsView> getStats() {

        StatsView stats = statsService.getStats();

        return ResponseEntity.
                ok(stats);
    }


    }





