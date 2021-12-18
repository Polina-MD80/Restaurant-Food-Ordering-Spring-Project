package softuni.restaurant.web.employees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.OrderService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;
import softuni.restaurant.web.exception.ObjectNotFoundException;

@Controller
public class EmployeeController {
    private final OrderService orderService;
    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/terminal")
    public String employee(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "terminal";
    }


    @GetMapping("/terminal/order/operate/{id}")
    public String operateOrder(@PathVariable Long id,
                               @AuthenticationPrincipal RestaurantUser user) {
        if (user == null) {
            return "redirect:/users/login";
        }
        UserEntity userEntity = userService.getUserByLoggedInUser(user);
        orderService.setNewStatus(id, userEntity);


        return "redirect:/terminal";
    }

    @GetMapping("/terminal/order/{id}")
    public String seeOrder(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal RestaurantUser user) {
        if (user == null) {
            return "redirect:/users/login";
        }
        try {
            model.addAttribute("order", orderService.findOrderById(id));
        } catch (ObjectNotFoundException exception) {
            model.addAttribute("notFound", exception.getMessage());
            return "redirect:/terminal";
        }


        return "order-terminal";
    }

    @Scheduled(cron = "${schedulers.cron1}")
    @GetMapping("/terminal/delete-on-schedule")
    public void deleteFromOrders() {
        try {
            orderService.deleteAllOldOrders();
        } catch (Exception e) {
            LOGGER.info("There was an error while deleting old orders");
        }
    }

    @DeleteMapping("/terminal/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            orderService.deleteOrderById(id);
            redirectAttributes.addFlashAttribute("success", "Order with id " + id + " has been delete successfully.");
        } catch (ObjectNotFoundException ex) {
            redirectAttributes.addFlashAttribute("success", ex.getMessage());
        }
        return "redirect:/terminal";
    }
}



