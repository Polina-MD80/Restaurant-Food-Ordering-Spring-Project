package softuni.restaurant.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;

import java.util.Arrays;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AllergenRepositoryTest {
    @Autowired
    AllergenRepository allergenRepository;

    @Test
    public void createAllergen() {
        AllergenEntity allergenEntity = new AllergenEntity(AllergenEnumName.MILK, AllergenEnumName.MILK.getImageUrl());
        AllergenEntity saved = allergenRepository.save(allergenEntity);

        Assertions.assertTrue(saved.getId() > 0);
    }

    @Test
    public void createAllAllergens(){
        Arrays.stream(AllergenEnumName.values()).forEach(allergenEnumName -> {
            AllergenEntity allergenEntity = new AllergenEntity(allergenEnumName, allergenEnumName.getImageUrl());
            AllergenEntity saved = allergenRepository.save(allergenEntity);
            Assertions.assertTrue(saved.getId() > 0);
        });
    }
}