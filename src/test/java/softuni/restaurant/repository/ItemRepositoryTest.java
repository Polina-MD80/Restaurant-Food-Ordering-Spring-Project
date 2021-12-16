package softuni.restaurant.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;
import softuni.restaurant.model.entity.enums.TypeEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class ItemRepositoryTest {

    private ItemEntity itemEntityOther;
    private ItemEntity itemEntityFood;
    private ItemEntity itemEntityDrink;
    private CategoryEntity categoryEntity1;
    private CategoryEntity categoryEntity2;
    private AllergenEntity allergenEntity1;
    private AllergenEntity allergenEntity2;
    private ProductEntity productEntity1;
    private ProductEntity productEntity2;

    @Autowired
    ItemRepository itemRepository;
    @Mock
    CategoryRepository mockCategoryRepository;
    @Mock
    AllergenRepository mockAllergenRepository;
    @Mock
    ProductRepository mockProductRepository;




    @BeforeEach
    void init(){

        categoryEntity1 = new CategoryEntity("Soup");
        categoryEntity2 = new CategoryEntity("Beverage");

        allergenEntity1 = new AllergenEntity(AllergenEnumName.MILK,"");
        allergenEntity2 = new AllergenEntity(AllergenEnumName.WHEAT,"");


        productEntity1 = new ProductEntity().setName("yogurt");
        productEntity2 = new ProductEntity().setName("flower");



        itemEntityOther = new ItemEntity()
                .setName("testItemOther")
                .setActive(true)
                .setCategories(Set.of(categoryEntity1, categoryEntity2))
                .setAllergens(Set.of(allergenEntity1, allergenEntity2))
                .setDescription("some description")
                .setManufacturer("MilkWay")
                .setPrice(BigDecimal.ONE)
                .setType(TypeEnum.OTHER)
                .setVolume(20)
                .setWeight(200)
                .setProducts(Set.of(productEntity1, productEntity2));

        itemEntityFood = new ItemEntity().setActive(false).setName("TestItemFood").setPrice(BigDecimal.TEN).setType(TypeEnum.FOOD);
        itemEntityDrink = new ItemEntity().setActive(false).setName("TestItemDrink").setPrice(BigDecimal.TEN).setType(TypeEnum.DRINK);
        itemRepository.save(itemEntityOther);
        itemRepository.save(itemEntityFood);
        itemRepository.save(itemEntityDrink);

    }

    @Test
    void findByName() {
        ItemEntity actual = itemRepository.findByName("testItemOther").orElse(new ItemEntity());
        Assertions.assertTrue(actual.getId()>0);
    }

    @Test
    void findById() {
        ItemEntity actual = itemRepository.findByName("testItemOther").orElse(new ItemEntity());
        Assertions.assertEquals(actual.getName(), itemRepository.findById(actual.getId()).get().getName());
    }
    @Test
    void findByIdReturnException() {
       Assertions.assertTrue(itemRepository.findById(-1L).isEmpty());
    }

    @Test
    void findAll() {
        Assertions.assertTrue(itemRepository.findAll().size()>1);
    }

    @Test
    void existsByName() {
        Optional<ItemEntity> x = itemRepository.findByName("TestItemFood");
        Assertions.assertFalse(x.isEmpty());
    }
    @Test
    void existsByNameReturnNull() {
        Optional<ItemEntity> x = itemRepository.findByName("XYZ");
        Assertions.assertTrue(x.isEmpty());
    }

    @Test
    void allFoods() {
        List<ItemEntity> foods = itemRepository.allFoods();
        Assertions.assertTrue(foods.size()>0);

        foods.forEach(itemEntity ->  Assertions.assertEquals(itemEntity.getType(),TypeEnum.FOOD));

    }

    @Test
    void allDrinks() {

        List<ItemEntity> drinks = itemRepository.allDrinks();
        Assertions.assertTrue(drinks.size()>0);

        drinks.forEach(itemEntity ->  Assertions.assertEquals(itemEntity.getType(),TypeEnum.DRINK));
    }

    @Test
    void allOther() {
        List<ItemEntity> others = itemRepository.allOther();
        Assertions.assertTrue(others.size()>0);
        others.forEach(itemEntity ->  Assertions.assertEquals(itemEntity.getType(),TypeEnum.OTHER));
    }

    @Test
    void search() {
        List<ItemEntity> soup = itemRepository.search("soup");
        Assertions.assertTrue(soup.size()>0);
    }
}