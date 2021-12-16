package softuni.restaurant.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class UserRepositoryTest {
    private UserEntity testUser;

    @Autowired
    UserRepository userRepository;


   @BeforeEach
    public void init() {
        testUser = new UserEntity()
        .setEmail("test@test.test")
        .setUsername("test")
        .setPassword("test")
        .setRole(RoleEnum.EMPLOYEE);

       userRepository.save(testUser);



    }


    @Test
    public void findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        Assertions.assertTrue(userEntities.size()>0);
    }

    @Test
    public void findUserById() {
        Long expected = testUser.getId();
        Long actual = userRepository.findById(testUser.getId()).get().getId();
        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateUser() {
        UserEntity userEntity = userRepository.findById(testUser.getId()).get();
        userEntity.setActive(false);
        userRepository.save(userEntity);
        assertFalse(testUser.isActive());
    }

    @Test
    public void deleteUser() {
       Long id = testUser.getId();
       userRepository.delete(testUser);
       assertTrue(userRepository.findById(id).isEmpty());


    }

    @Test
    public void findByUsernameTest() {
        Optional<UserEntity> byUsername = userRepository.findByUsername(testUser.getUsername());
        Long actual = byUsername.get().getId();

        assertEquals(testUser.getId(), actual);

    }

    @Test
    public void findByUsernameIgnoreCaseTest(){
        String toRandomCase = testUser.getUsername().substring(0, 1).toUpperCase(Locale.ROOT) + testUser.getUsername().substring(1);
        assertEquals(userRepository.findByUsernameIgnoreCase(testUser.getUsername()).get().getId(),
                userRepository.findByUsernameIgnoreCase(toRandomCase).get().getId());
    }

}