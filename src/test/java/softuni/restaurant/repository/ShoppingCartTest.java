package softuni.restaurant.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ShoppingCartTest {

    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testAddCartDetail(){
        ItemEntity itemEntity = testEntityManager.find(ItemEntity.class, 2L);
        UserEntity userEntity = testEntityManager.find(UserEntity.class, 3L);

        CartDetailEntity cartDetailEntity = new CartDetailEntity();
        cartDetailEntity.setItem(itemEntity);
        cartDetailEntity.setUser(userEntity);
        cartDetailEntity.setQuantity(4);

        CartDetailEntity entity = cartDetailRepository.save(cartDetailEntity);

        Assert.assertTrue(entity.getId()>0);
    }

    @Test
    public void getCartItemsByUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3L);

        List<CartDetailEntity> cartDetailEntities = cartDetailRepository.findByUser(userEntity);

        Assert.assertEquals(0, cartDetailEntities.size());
    }

}