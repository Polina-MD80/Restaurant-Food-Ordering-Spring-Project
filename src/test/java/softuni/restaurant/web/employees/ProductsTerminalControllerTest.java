package softuni.restaurant.web.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.*;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.repository.*;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsTerminalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Autowired
    AllergenRepository allergenRepository;

    private AllergenEntity allergenEntity;
    private ProductEntity testProduct;


    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = initUser();
        userRepository.save(testUser);
        testProduct = productRepository.save(new ProductEntity().setName("papaya"));
    }

    @AfterEach
    void tearDown() throws Exception {
        userRepository.delete(testUser);
        productRepository.delete(testProduct);
    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void productsPageOpens() throws Exception {
        mockMvc
                .perform(get("/terminal/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products-terminal"))
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(model().attributeExists("allergens"));
    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void testAddProductCorrect() throws Exception {

        mockMvc
                .perform(post("/terminal/products/add")
                        .param("name", "desert")
                        .param("allergens", "")
                        .with(csrf())


                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/terminal/products"));

        Optional<ProductEntity> desert = productRepository.findByName("desert");
        assertTrue(desert.isPresent());
        assertTrue(desert.get().getId() > 0);

        productRepository.delete(desert.get());
    }
    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void testEditProductCorrect() throws Exception {

        mockMvc
                .perform(patch("/terminal/products/edit/" + testProduct.getId())
                        .param("name", "melon")
                        .param("allergens", "")
                        .with(csrf())


                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/terminal/products"));

        Optional<ProductEntity> melon = productRepository.findByName("melon");
        assertTrue(melon.isPresent());
        assertTrue(productRepository.findByName("papaya").isEmpty());



    }


    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.ADMIN);
    }
}