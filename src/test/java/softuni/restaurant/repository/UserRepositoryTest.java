package softuni.restaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
 @Autowired
    UserRepository userRepository;

 @Test
    public void createUser(){
     UserEntity userEntity = new UserEntity();
     userEntity.setEmail("abv@abv.abv");
     userEntity.setUsername("abv");
     userEntity.setPassword("abv");
     userEntity.setRole(RoleEnum.EMPLOYEE);

     UserEntity save = userRepository.save(userEntity);

     assertTrue(save.getId()>0);

 }
}