package softuni.restaurant.service;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.view.CartDetailViewModel;
import softuni.restaurant.service.impl.RestaurantUser;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
  List<CartDetailViewModel> listOfCartDetails(RestaurantUser user);

  Integer addItem(Long itemId, Integer quantityToAdd, UserEntity user);

  BigDecimal updateQuantity(Integer quantity, Long itemId, UserEntity user);

  void removeItem(Long itemId, UserEntity userEntity);

    void emptyCart(Long customerId);

}
