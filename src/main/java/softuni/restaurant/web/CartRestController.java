package softuni.restaurant.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.CartService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;

import java.math.BigDecimal;

@RestController
public class CartRestController {

    private final CartService cartService;
    private final UserService userService;

    public CartRestController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/cart/add/{iid}")
    public String addToCart(@PathVariable("iid") Long itemId,
                            @RequestParam("qty") Integer quantity,
                            @AuthenticationPrincipal RestaurantUser user) {
        System.out.println("Trying to add item " + itemId);

        if (user == null) {
            return "you must login before adding to cart";
        }

        UserEntity userEntity = userService.getUserByLoggedInUser(user);
        Integer addedQty = cartService.addItem(itemId, quantity, userEntity);

        System.out.println("added item " + itemId);
        return addedQty + " item(s) added to your cart.";
    }

    @PostMapping("/cart/update/{iid}/{qty}")
    public String updateQuantity(@PathVariable("iid") Long itemId, @PathVariable("qty") Integer quantity,
                                 @AuthenticationPrincipal RestaurantUser user) {
      System.out.println("trying to add item " + itemId);

        if (user == null) {
            return "you must login before updating cart";
        }

        UserEntity userEntity = userService.getUserByLoggedInUser(user);
        BigDecimal newSubTotal = cartService.updateQuantity(quantity, itemId, userEntity);


        return String.valueOf(newSubTotal);
    }

    @PostMapping("/cart/remove/{iid}")
    public String removeItemFromCart(@PathVariable("iid") Long itemId,
                                     @AuthenticationPrincipal RestaurantUser user) {
//        System.out.println("trying to add item " + itemId);

        if (user == null) {
            return "you must login before removing item";
        }

        UserEntity userEntity = userService.getUserByLoggedInUser(user);
        cartService.removeItem(itemId, userEntity);


        return "The item has been successfully removed from your cart.";
    }
}
