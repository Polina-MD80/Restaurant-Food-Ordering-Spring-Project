package softuni.restaurant.web.customers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.constants.RestaurantConstantImages;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.view.CartDetailViewModel;
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

        List<CartDetailViewModel> cartDetails= cartService.listOfCartDetails(user);
        BigDecimal estimatedTotal = BigDecimal.ZERO;
        for (CartDetailViewModel cartDetail : cartDetails) {
          estimatedTotal= estimatedTotal.add(cartDetail.getSubTotal());
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("estimatedTotal", estimatedTotal);
        model.addAttribute("estimatedTotal", RestaurantConstantImages.DEFAULT_IMAGE);
        return "cart";
    }

    @PostMapping("/cart/add/{iid}")
    public String addToCart(@PathVariable("iid") Long itemId,
                            @RequestParam("qty") Integer quantity,
                            @AuthenticationPrincipal RestaurantUser user,
                            RedirectAttributes redirectAttributes) {


        if (user == null) {
            return "redirect:/users/login";
        }

        UserEntity userEntity = userService.getUserByLoggedInUser(user);
        Integer addedQty = cartService.addItem(itemId, quantity, userEntity);
        redirectAttributes.addFlashAttribute("success", addedQty + " item(s) new added to your cart.");

        return "redirect:/cart";
    }


}