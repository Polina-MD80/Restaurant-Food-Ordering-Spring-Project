package softuni.restaurant.web.customers;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.OrderEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.view.CartDetailViewModel;
import softuni.restaurant.service.CartService;
import softuni.restaurant.service.OrderService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(UserService userService, CartService cartService, OrderService orderService, ModelMapper modelMapper) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("order")
    public OrderEntity order() {
        return new OrderEntity();
    }

    @GetMapping("/order")
    public String order(Model model, @ModelAttribute("order") OrderEntity order,
                        @AuthenticationPrincipal RestaurantUser user) {


        List<CartDetailViewModel> cartDetails = cartService.listOfCartDetails(user);
        BigDecimal total = BigDecimal.ZERO;
        for (CartDetailViewModel cartDetail : cartDetails) {
            total = total.add(cartDetail.getSubTotal());
        }
        model.addAttribute("order", order);
        model.addAttribute("cartDetails", cartDetails);
          model.addAttribute("user", userService.getUserByLoggedInUser(user));
        model.addAttribute("total", total);



        return "order";
    }

    @PostMapping("/order-place")
    public String order(@ModelAttribute("order") OrderEntity order, RedirectAttributes redirectAttributes,
                        @AuthenticationPrincipal RestaurantUser user) {


        boolean ordered = orderService.saveOrder(order, user);

        redirectAttributes.addFlashAttribute("ordered", ordered);


        return "redirect:/cart";
    }

}
