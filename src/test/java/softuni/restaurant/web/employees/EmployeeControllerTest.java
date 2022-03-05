package softuni.restaurant.web.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.OrderEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.repository.OrderItemRepository;
import softuni.restaurant.repository.OrderRepository;
import softuni.restaurant.repository.UserRepository;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.OrderItemService;
import softuni.restaurant.service.OrderService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ModelMapper modelMapper;

    private UserEntity user;
    private UserEntity customer;
    private OrderItemEntity orderItemEntity1;
    private OrderItemEntity orderItemEntity2;
    private OrderEntity order;
    private ItemEntity itemEntity1;
    private ItemEntity itemEntity2;

    @BeforeEach
    void setUp() {
//        user = initUser();
//        userService.saveUser(user);
//        customer = initCustomer();
//        userService.saveUser(customer);
////        initItemEntities();
////        order = initOrder();
////        orderRepository.save(order);


    }

    @AfterEach
    void tearDown() {
//        orderRepository.delete(order);
//        orderItemRepository.delete(orderItemEntity1);
//        orderItemRepository.delete(orderItemEntity2);
//        itemRepository.delete(itemEntity1);
//        itemRepository.delete(itemEntity2);
       userRepository.delete(customer);
       userRepository.delete(user);
    }

    private void initItemEntities() {
        itemEntity1 = itemRepository.save(new ItemEntity().setName("testItem1").setActive(true).setPrice(BigDecimal.TEN).setType(TypeEnum.FOOD));
        itemEntity2 = itemRepository.save(new ItemEntity().setName("testItem2").setActive(true).setPrice(BigDecimal.TEN).setType(TypeEnum.DRINK));

    }

    private OrderEntity initOrder() {
        orderItemEntity1 = orderItemRepository.save(new OrderItemEntity().setItem(itemEntity1).setQuantity(5));
        orderItemEntity2 = orderItemRepository.save( new OrderItemEntity().setItem(itemEntity2).setQuantity(4));
        return new OrderEntity().setAddress("testAddress")
                .setEmail("new@email").setEmployee(user).setCustomer(customer)
                .setItems(Set.of(orderItemEntity1, orderItemEntity2)).setPhone("0888888888");
    }




    private UserEntity initCustomer() {
        return new UserEntity().setUsername("cust").setPassword(passwordEncoder.encode("cust"))
                .setActive(true).setEmail("cust@cust.com").setRole(RoleEnum.CUSTOMER);
    }


    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.ADMIN);
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void openTerminalPage() throws Exception {
        mockMvc
                .perform(get("/terminal"))
                .andExpect(status().isOk())
                .andExpect(view().name("terminal"))
                .andExpect(model().attributeExists("orders"));

    }

    @Test
    void employee() {
    }

    @Test
    void operateOrder() {
    }

    @Test
    void seeOrder() {
    }

    @Test
    void deleteFromOrders() {
    }

    @Test
    void deleteOrder() {
    }
}