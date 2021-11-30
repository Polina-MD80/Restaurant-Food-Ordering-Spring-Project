package softuni.restaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;


    @Test
    public void createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("abv@abv.abv");
        userEntity.setUsername("abv");
        userEntity.setPassword("abv");
        userEntity.setRole(RoleEnum.EMPLOYEE);

        UserEntity save = userRepository.save(userEntity);

        assertTrue(save.getId() > 0);

    }

    @Test
    public void findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        userEntities.forEach(System.out::println);
    }

    @Test
    public void findUserById() {
        UserEntity userEntity = userRepository.findById(1L).get();
        assertNotNull(userEntity);

    }

    @Test
    public void testUpdateUser() {
        UserEntity userEntity = userRepository.findById(1L).get();
        userEntity.setActive(false);
        userRepository.save(userEntity);
    }

@Test
    public void deleteUser(){
    UserEntity userEntity = userRepository.findById(1L).get();
    userRepository.delete(userEntity);

}
}