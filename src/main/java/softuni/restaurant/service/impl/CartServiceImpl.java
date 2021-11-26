package softuni.restaurant.service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.repository.CartDetailRepository;
import softuni.restaurant.service.CartService;
import softuni.restaurant.service.ItemService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final CartDetailRepository cartDetailRepository;
    private final ItemService itemService;


    public CartServiceImpl(CartDetailRepository cartDetailRepository, ItemService itemService) {
        this.cartDetailRepository = cartDetailRepository;

        this.itemService = itemService;
    }

    @Override
    public List<CartDetailEntity> listOfCartDetails(UserEntity userEntity) {
        return cartDetailRepository.findByUser(userEntity);
    }


    public Integer addItem(Long itemId, Integer quantityToAdd, UserEntity user) {
        Integer quantity = quantityToAdd;

        ItemEntity itemEntity = itemService.findById(itemId);

        CartDetailEntity cartDetailEntity = cartDetailRepository.findByUserAndItem(user, itemEntity);

        if (cartDetailEntity != null) {
            quantity = cartDetailEntity.getQuantity() + quantityToAdd;

            cartDetailEntity.setQuantity(quantity);
        } else {
            cartDetailEntity = new CartDetailEntity();
            cartDetailEntity.setUser(user).setItem(itemEntity).setQuantity(quantity);
        }

        cartDetailRepository.save(cartDetailEntity);

        return quantity;

    }

    @Override
    public BigDecimal updateQuantity(Integer quantity, Long itemId, UserEntity user) {
        cartDetailRepository.updateQuantity(quantity, itemId, user.getId());

        ItemEntity item = itemService.findById(itemId);

        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public void removeItem(Long itemId, UserEntity userEntity) {
        cartDetailRepository.deleteByUserAndItem(itemId, userEntity.getId());
    }
}
