package softuni.restaurant.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.ItemEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    private ItemEntity itemEntity;

    @Autowired
    ItemRepository itemRepository;
    @BeforeEach
    void init(){
        itemEntity = new ItemEntity()
                .setActive(true);

    }

    @Test
    void findByName() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void existsByName() {
    }

    @Test
    void allFoods() {
    }

    @Test
    void allDrinks() {
    }

    @Test
    void allOther() {
    }

    @Test
    void search() {
        List<ItemEntity> soup = itemRepository.search("soup");
        Assertions.assertTrue(soup.size()>0);
    }
}