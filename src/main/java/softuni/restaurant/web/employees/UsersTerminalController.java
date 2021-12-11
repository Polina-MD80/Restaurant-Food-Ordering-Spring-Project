package softuni.restaurant.web.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.service.UserService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

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
        return "users-terminal";
    }

    @GetMapping("new-user")
    public String addNewUser(Model model){
        UserEntity user = new UserEntity();
        user.setActive(true);
        user.setRole(RoleEnum.CUSTOMER);
        model.addAttribute("user", user);

        return "user-form";
    }

    @PostMapping("save-user")
    public String saveUser(UserEntity user, RedirectAttributes redirectAttributes){
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "User '" + user.getUsername()+ "' has been saved successfully");
        return "redirect:users";
    }

    @GetMapping("users/edit/{id}")
    public String editUser(@PathVariable ("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            UserEntity userById = userService.getUserBYId(id);
            model.addAttribute("user", userById);
            return "user-form";
        }catch (ObjectNotFoundException ex){
            redirectAttributes.addFlashAttribute("success", ex.getMessage());
        }
        return "redirect:/terminal/users";
    }

    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable ("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            UserEntity userById = userService.getUserBYId(id);
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("success", "User with id " + id +" has been delete successfully.");
        }catch (ObjectNotFoundException ex){
            redirectAttributes.addFlashAttribute("success", ex.getMessage());
        }
        return "redirect:/terminal/users";
    }
}
