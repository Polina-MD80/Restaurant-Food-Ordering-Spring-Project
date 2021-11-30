package softuni.restaurant.web.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.UserService;

import java.util.List;

@Controller
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("terminal/users")
    public String listAllUsers(Model model){
        List<UserEntity> allUsers = userService.allUsers();
        model.addAttribute("allUsers",allUsers);
        return "terminal-users";
    }

    @GetMapping("users/new")
    public String addNewUser(){
        return "new-user";
    }
}
