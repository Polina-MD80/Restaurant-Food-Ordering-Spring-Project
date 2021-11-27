package softuni.restaurant.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.CartService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/cart")

    public String getCart(Model model,
                          @AuthenticationPrincipal RestaurantUser user) {
        UserEntity userEntity = userService
                .getUserByLoggedInUser(user);
        List<CartDetailEntity> cartDetails= cartService.listOfCartDetails(userEntity);
        model.addAttribute("cartDetails", cartDetails);
        return "cart";
    }
}