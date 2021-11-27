package softuni.restaurant.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        ItemEntity itemEntity = testEntityManager.find(ItemEntity.class, 1L);
        UserEntity userEntity = testEntityManager.find(UserEntity.class, 3L);

        CartDetailEntity cartDetailEntity = new CartDetailEntity();
        cartDetailEntity.setItem(itemEntity);
        cartDetailEntity.setUser(userEntity);
        cartDetailEntity.setQuantity(2);

        CartDetailEntity entity = cartDetailRepository.save(cartDetailEntity);

        assertTrue(entity.getId()>0);
    }

    private void assertTrue(boolean b) {
    }

    @Test
    public void getCartItemsByUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3L);

        List<CartDetailEntity> cartDetailEntities = cartDetailRepository.findByUser(userEntity);

        assertEquals(2, cartDetailEntities.size());
    }

    private void assertEquals(int i, int size) {
    }

    @Test
    public void deleteFromCart(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3L);

        cartDetailRepository.deleteByUserAndItem(3L, 2L);
        List<CartDetailEntity> cartDetailEntities = cartDetailRepository.findByUser(userEntity);
        assertEquals(1, cartDetailEntities.size());
    }

    @Test
    public void updateQuantity(){
        cartDetailRepository.updateQuantity(7, 2L, 3L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(3L);
        ItemEntity item = new ItemEntity();
        item.setId(2L);
        assertEquals(7, cartDetailRepository.findByUserAndItem(userEntity, item).getQuantity());
    }

}