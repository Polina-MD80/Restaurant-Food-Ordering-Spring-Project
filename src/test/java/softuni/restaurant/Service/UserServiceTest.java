package softuni.restaurant.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.service.UserRegistrationServiceModel;
import softuni.restaurant.repository.UserRepository;
import softuni.restaurant.service.UserService;
import softuni.restaurant.service.impl.RestaurantUser;
import softuni.restaurant.service.impl.RestaurantUserServiceImpl;
import softuni.restaurant.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserRegistrationServiceModel testUserToRegister;
    private UserServiceImpl serviceToTest;
    private UserEntity testUser;
    private List<GrantedAuthority> authorities;

    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private RestaurantUserServiceImpl mockRestaurantUserService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new Pbkdf2PasswordEncoder();
        serviceToTest = new UserServiceImpl(mockPasswordEncoder, mockUserRepository, mockRestaurantUserService);
        testUser = new UserEntity().setUsername("test").setEmail("test@test").setPassword(passwordEncoder.encode("test")).setRole(RoleEnum.CUSTOMER);
        testUserToRegister = new UserRegistrationServiceModel().setUsername("test").setEmail("test@test").setPassword("test");
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + testUser.getRole().name()));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerAndLoginUserTest() {
        Mockito.when(mockUserRepository.findByUsername("test")).thenReturn(Optional.ofNullable(testUser));
        Optional<UserEntity> test = mockUserRepository.findByUsername("test");
        assertEquals(test.get().getUsername(), testUser.getUsername());
        assertSame(test.get().getPassword(), testUser.getPassword());

    }

    @Test
    void isUserNameFreeTest() {
        Mockito.when(mockUserRepository.findByUsernameIgnoreCase("test")).thenReturn(Optional.ofNullable(testUser));
        assertSame("test", mockUserRepository.findByUsernameIgnoreCase("test").get().getUsername());
        assertNotEquals("lala", mockUserRepository.findByUsernameIgnoreCase("test").get().getUsername());
    }


    @Test
    void getUserByLoggedInUserTest() {
        RestaurantUser restaurantUser = new RestaurantUser(testUser.getUsername(), testUser.getPassword(), authorities);
        assertEquals(restaurantUser.getUserIdentifier(), testUser.getUsername());
    }



    @Test
    void saveUser() {

        Mockito.when(mockUserRepository.save(testUser)).thenReturn((UserEntity) testUser.setId(-1L));
        serviceToTest.saveUser(testUser,null);
        Assertions.assertEquals(-1L, testUser.getId());
        Assertions.assertEquals("test", testUser.getUsername());

    }

    @Test
    void getUserBYId() {
        Mockito.when(mockUserRepository.save(testUser)).thenReturn((UserEntity) testUser.setId(-1L));
        serviceToTest.saveUser(testUser,null);
        Assertions.assertEquals(-1L, testUser.getId());
    }



    @Test
    void usernameFree() {

        Mockito.when(mockUserRepository.findByUsername("test")).thenReturn(Optional.ofNullable(testUser));
        assertFalse(serviceToTest.usernameFree("test"));
    }
}