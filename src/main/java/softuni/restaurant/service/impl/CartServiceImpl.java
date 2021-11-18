package softuni.restaurant.service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.repository.CartDetailRepository;
import softuni.restaurant.service.CartService;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    private final CartDetailRepository cartDetailRepository;

    public CartServiceImpl(CartDetailRepository cartDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public List<CartDetailEntity> listOfCartDetails(UserEntity userEntity) {
        return cartDetailRepository.findByUser(userEntity);
    }
}
