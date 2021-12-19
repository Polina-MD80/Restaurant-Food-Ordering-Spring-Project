package softuni.restaurant.web.employees;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softuni.restaurant.service.UserService;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("terminal/check-username")
    public String checkUsernameIsFree( Long id, String username){
        if (userService.isUserNameFree(id,username)){
            return "OK";
        }
        return "Duplicated";
    }
}
