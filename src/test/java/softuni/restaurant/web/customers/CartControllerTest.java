package softuni.restaurant.web.customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.repository.*;
import softuni.restaurant.service.*;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    ModelMapper modelMapper;

    private UserEntity testUser;
    private ItemEntity testItem;
    private CartDetailEntity testCartDetailEntity;

    @BeforeEach
    void setUp() {
        testUser = initUser();
        userRepository.save(testUser);
        testItem = itemRepository.save(new ItemEntity().setName("itemFromCategory").setActive(true).
                setType(TypeEnum.FOOD).setPrice(BigDecimal.TEN));
        testCartDetailEntity = cartDetailRepository.save(new CartDetailEntity()
                .setItem(testItem).setUser(testUser).setQuantity(1));
    }

    @AfterEach
    void tearDown() {
        cartDetailRepository.delete(testCartDetailEntity);
        itemRepository.delete(testItem);
        userRepository.delete(testUser);
    }


    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "CUSTOMER")
    void openCartPage() throws Exception {
        mockMvc
                .perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cartDetails"))
                .andExpect(model().attributeExists("estimatedTotal"));


    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "CUSTOMER")
    void addToCartPostFails() throws Exception {
        mockMvc.perform(post("/cart/add/" + testItem.getId())
                .param("qty", String.valueOf(5))
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
//TODO how to get the mock user as principal
    }


    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.CUSTOMER);
    }

}