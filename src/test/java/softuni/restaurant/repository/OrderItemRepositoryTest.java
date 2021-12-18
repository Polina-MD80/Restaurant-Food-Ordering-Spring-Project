package softuni.restaurant.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.model.entity.enums.TypeEnum;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class OrderItemRepositoryTest {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private ItemEntity item1;
    private ItemEntity item2;
    private OrderItemEntity orderItemEntity1;
    private OrderItemEntity orderItemEntity2;
    @BeforeEach
    void setUp() {
        item1 = testEntityManager.persist(new ItemEntity().setName("item11").setActive(true).setPrice(BigDecimal.TEN).setType(TypeEnum.FOOD));
        item2 = testEntityManager.persist(new ItemEntity().setName("item22").setActive(true).setPrice(BigDecimal.ONE).setType(TypeEnum.DRINK));
        orderItemEntity1 = testEntityManager.persist(new OrderItemEntity().setItem(item1).setQuantity(11));
        orderItemEntity2 = testEntityManager.persist(new OrderItemEntity().setItem(item2).setQuantity(12));
    }

    @Test
    public void testSaveOrderItemEntity(){
        OrderItemEntity saved = orderItemRepository.save(new OrderItemEntity().setItem(item1).setQuantity(1));
        assertTrue(saved.getId()>0);
    }
    @Test
    public void deleteTest(){
        orderItemRepository.delete(orderItemEntity1);
        assertTrue(orderItemRepository.findById(orderItemEntity1.getId()).isEmpty());
    }
    @Test
    public void testPrePersistMethod(){
        assertEquals(BigDecimal.valueOf(110), orderItemEntity1.getSubtotal());
    }


}