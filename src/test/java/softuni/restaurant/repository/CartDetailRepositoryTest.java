package softuni.restaurant.repository;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.entity.enums.TypeEnum;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class CartDetailRepositoryTest {

    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private ItemEntity item1;
    private ItemEntity item2;
    private UserEntity user1;
    private UserEntity user2;
    private CartDetailEntity cartDetailEntity1;
    private CartDetailEntity cartDetailEntity2;
    private CartDetailEntity cartDetailEntity3;

    @BeforeEach
    void setUp() {
        item1 = testEntityManager.persist(new ItemEntity().setName("item1").setActive(true).setPrice(BigDecimal.TEN).setType(TypeEnum.FOOD));
        item2 = testEntityManager.persist(new ItemEntity().setName("item2").setActive(true).setPrice(BigDecimal.ONE).setType(TypeEnum.DRINK));
        user1 = testEntityManager.persist(new UserEntity().setUsername("user1").setPassword("user1").setRole(RoleEnum.CUSTOMER).setEmail("user1@email"));
        user2 = testEntityManager.persist(new UserEntity().setUsername("user2").setPassword("user2").setRole(RoleEnum.ADMIN).setEmail("user2@email"));
        cartDetailEntity1 = new CartDetailEntity();
        cartDetailEntity2 = new CartDetailEntity();
        cartDetailEntity3 = new CartDetailEntity();
        cartDetailEntity1 = testEntityManager.persist(new CartDetailEntity().setItem(item1).setQuantity(1).setUser(user1));
        cartDetailEntity2 = testEntityManager.persist(new CartDetailEntity().setItem(item2).setQuantity(2).setUser(user2));
        cartDetailEntity3 = testEntityManager.persist(new CartDetailEntity().setItem(item1).setQuantity(1).setUser(user2));

    }

    @Test
    public void addCartDetailTest() {
        CartDetailEntity cartDetailEntity = new CartDetailEntity();
        cartDetailEntity.setItem(item1);
        cartDetailEntity.setUser(user1);
        cartDetailEntity.setQuantity(2);

        CartDetailEntity entity = cartDetailRepository.save(cartDetailEntity);

        assertTrue(entity.getId() > 0);
    }



    @Test
    public void getCartItemsByUserTest() {


        List<CartDetailEntity> cartDetailEntities1 = cartDetailRepository.findByUser(user1);
        List<CartDetailEntity> cartDetailEntities2 = cartDetailRepository.findByUser(user2);
        assertEquals(1, cartDetailEntities1.size());
        assertEquals(2, cartDetailEntities2.size());

    }



    @Test
    public void deleteByUserAndItemFromCartTest() {

        cartDetailRepository.deleteByUserAndItem(item1.getId(), user1.getId());
        cartDetailRepository.deleteByUserAndItem(item1.getId(), user2.getId());
        List<CartDetailEntity> cartDetailEntities1 = cartDetailRepository.findByUser(user1);
        List<CartDetailEntity> cartDetailEntities2 = cartDetailRepository.findByUser(user2);
        assertEquals(0, cartDetailEntities1.size());
        assertEquals(1, cartDetailEntities2.size());
    }

    @Test
    public void updateQuantityTest() {
        cartDetailRepository.updateQuantity(7, item2.getId(), user2.getId());
        testEntityManager.clear();
        assertEquals(7, cartDetailRepository.findByUserAndItem(user2, item2).getQuantity());
    }


    @Test
    void findByUserTest() {
        List<CartDetailEntity> byUser = cartDetailRepository.findByUser(user1);
        assertEquals(item1, byUser.get(0).getItem());
        assertEquals(1, byUser.get(0).getQuantity());
    }

    @Test
    void findByUserAndItem() {
        CartDetailEntity actual = cartDetailRepository.findByUserAndItem(user1, item1);
        assertEquals(user1, actual.getUser());
        assertEquals(item1, actual.getItem());
        assertEquals(1, actual.getQuantity());
    }




    @Test
    void emptyCart() {
      cartDetailRepository.emptyCart(user2.getId());
      testEntityManager.clear();
        List<CartDetailEntity> actual = cartDetailRepository.findByUser(user2);
        assertEquals(0, actual.size());
    }
}