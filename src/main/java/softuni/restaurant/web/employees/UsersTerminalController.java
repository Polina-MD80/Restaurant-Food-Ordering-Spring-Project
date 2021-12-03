package softuni.restaurant.web.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.UserService;

import java.util.List;

@Controller
@RequestMapping("terminal")
public class UsersTerminalController {
    private final UserService userService;

    public UsersTerminalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public String listAllUsers(Model model){
        List<UserEntity> allUsers = userService.allUsers();
        model.addAttribute("allUsers",allUsers);
        return "terminal-users";
    }

    @GetMapping("new-user")
    public String addNewUser(Model model){
        UserEntity user = new UserEntity();
        user.setActive(true);
        model.addAttribute("user", user);

        return "user-form";
    }

    @PostMapping("save-user")
    public String saveUser(UserEntity user, RedirectAttributes redirectAttributes){
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "User '" + user.getUsername()+ "' has been added successfully");
        return "redirect:users";
    }
}
