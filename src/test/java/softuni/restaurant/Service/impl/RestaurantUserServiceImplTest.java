package softuni.restaurant.Service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.repository.UserRepository;
import softuni.restaurant.service.impl.RestaurantUserServiceImpl;


import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantUserServiceImplTest {
    private UserEntity testUser;
    private RestaurantUserServiceImpl serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new RestaurantUserServiceImpl(mockUserRepository);
        testUser = new UserEntity().setUsername("test").setEmail("test@test").setPassword("test").setRole(RoleEnum.ADMIN);

    }

    @Test
    void testUserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_user_email@non_existing.com"));

    }

    @Test
    void testUserFound() {

        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        var actual = serviceToTest.loadUserByUsername(testUser.getUsername());


        Assertions.assertEquals(actual.getUsername(), testUser.getUsername());

        String actualRoles =
                actual
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "));

        Assertions.assertEquals(actualRoles, "ROLE_"+testUser.getRole().name());
    }


}