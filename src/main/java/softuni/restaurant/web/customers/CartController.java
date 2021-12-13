package softuni.restaurant.web.customers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.CartService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;

import java.math.BigDecimal;
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
//        if (user == null) {
//            return "redirect:/users/login";
//        }
        UserEntity userEntity = userService
                .getUserByLoggedInUser(user);
        List<CartDetailEntity> cartDetails= cartService.listOfCartDetails(userEntity);
        BigDecimal estimatedTotal = BigDecimal.ZERO;
        for (CartDetailEntity cartDetail : cartDetails) {
          estimatedTotal= estimatedTotal.add(cartDetail.getSubTotal());
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("estimatedTotal", estimatedTotal);
        return "cart";
    }

}