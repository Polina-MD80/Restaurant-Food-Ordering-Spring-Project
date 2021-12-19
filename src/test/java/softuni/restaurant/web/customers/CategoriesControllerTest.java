package softuni.restaurant.web.customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.repository.CategoryRepository;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;


    private CategoryEntity testCategory;

    private ItemEntity testItem;


    @BeforeEach
    void setUp() {
        testCategory = categoryRepository.save(new CategoryEntity().setName("testCategory"));
        testItem = itemRepository.save(new ItemEntity().setName("itemFromCategory").setActive(true).
                setCategories(Set.of(testCategory)).setType(TypeEnum.FOOD).setPrice(BigDecimal.TEN));
    }

    @AfterEach
    void tearDown() throws Exception {
        itemService.deleteItem(testItem.getId());
        categoryService.deleteCategory(testCategory.getId());

    }


    @Test
    void categoriesPageOpens() throws Exception {
        mockMvc
                .perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attributeExists("allCategories"));
    }

    @Test
    void categoryOpensFoodsByCategory() throws Exception {
        mockMvc
                .perform(get("/categories/cat/" + testCategory.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("foods"))
                .andExpect(model().attributeExists("itemsByType"));
    }

    @Test
    void categoryAddOpensForm() throws Exception {
        mockMvc
                .perform(get("/categories/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-add"))
                .andExpect(model().attributeExists("itemsByType"));
    }
}