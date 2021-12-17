package softuni.restaurant.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.OrderEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.entity.enums.TypeEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class OrderRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    OrderRepository orderRepository;

    private OrderItemEntity orderItemEntity1;
    private OrderItemEntity orderItemEntity2;
    private UserEntity user1;
    private UserEntity user2;
    private OrderEntity order1;
    private OrderEntity order2;


    @BeforeEach
    void setUp() {
        ItemEntity item1 = testEntityManager.persist(new ItemEntity().setName("item1").setActive(true).setPrice(BigDecimal.TEN).setType(TypeEnum.FOOD));
        ItemEntity item2 = testEntityManager.persist(new ItemEntity().setName("item2").setActive(true).setPrice(BigDecimal.ONE).setType(TypeEnum.DRINK));
        orderItemEntity1 = testEntityManager.persist(new OrderItemEntity().setItem(item1).setQuantity(11));
        orderItemEntity2 = testEntityManager.persist(new OrderItemEntity().setItem(item2).setQuantity(12));
        user1 = testEntityManager.persist(new UserEntity().setUsername("user1").setPassword("user1").setEmail("user1@user.com").setRole(RoleEnum.CUSTOMER));
        user2 = testEntityManager.persist(new UserEntity().setUsername("user2").setPassword("user2").setEmail("user1@user.com").setRole(RoleEnum.ADMIN));
        order1 = testEntityManager.persist(new OrderEntity().setPhone("0888888888")
                .setItems(Set.of(orderItemEntity1, orderItemEntity2))
                .setCustomer(user1).setAddress("user1Address").setEmail("order1@email"));
        order2 = testEntityManager.persist(new OrderEntity().setPhone("0888888888")
                .setItems(Set.of(orderItemEntity2))
                .setCustomer(user2).setAddress("user2Address").setEmail("order2@email"));
    }

    @Test
    public void saveAndDeleteOrderTest() {
        OrderEntity orderEntity = new OrderEntity().setAddress("test").setEmail("test@test.test").setCustomer(user1)
                .setAddress("testAddress1")
                .setPhone("0888888888")
                .setItems(Set.of(orderItemEntity1, orderItemEntity2));
        OrderEntity saved = orderRepository.save(orderEntity);
        long id = saved.getId();
        assertTrue(saved.getId() > 0);
        orderRepository.delete(orderEntity);
        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    public void deleteOrderTest() {
        long id = order1.getId();
        orderRepository.delete(order1);
        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    void findAllOrderByCreatedNewest() {
        List<OrderEntity> allOrderByCreatedNewest =
                orderRepository.findAllOrderByCreatedNewest();
        assertEquals(allOrderByCreatedNewest.get(0).getId(), order2.getId());
        assertEquals(allOrderByCreatedNewest.get(1).getId(), order1.getId());
        OrderEntity orderEntity = new OrderEntity().setAddress("test").setEmail("test@test.test").setCustomer(user1)
                .setAddress("testAddress1")
                .setPhone("0888888888")
                .setItems(Set.of(orderItemEntity1, orderItemEntity2));
        OrderEntity saved = orderRepository.save(orderEntity);
        List<OrderEntity> allAfterSaveNew = orderRepository.findAllOrderByCreatedNewest();
        assertEquals(allAfterSaveNew.get(0).getId(), saved.getId());
        assertEquals(allAfterSaveNew.get(1).getId(), order2.getId());
        assertEquals(allAfterSaveNew.get(2).getId(), order1.getId());

    }

    @Test
    void findAllAldOrders() {
        long count = orderRepository.count();
        List<OrderEntity> all = orderRepository.findAll();
        assertEquals(all.size(), count);
        orderRepository.delete(order1);
        List<OrderEntity> afterDelete = orderRepository.findAll();
        assertEquals(afterDelete.size(), count-1);
    }
}