package softuni.restaurant.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AllergenRepositoryTest {
    private AllergenEntity allergen1;
    private AllergenEntity allergen2;
    @Autowired
    AllergenRepository allergenRepository;

    @BeforeEach
    public void setUp() {
        allergen1 = new AllergenEntity(AllergenEnumName.MILK, AllergenEnumName.MILK.getImageUrl());
        allergen2 = new AllergenEntity(AllergenEnumName.WHEAT, AllergenEnumName.WHEAT.getImageUrl());

        allergenRepository.save(allergen1);
        allergenRepository.save(allergen2);


    }

    @Test
    public void findAllTest() {
        List<AllergenEntity> all = allergenRepository.findAll();
        assertTrue(all.size() > 0);
        assertTrue(all.stream().anyMatch(allergenEntity -> allergenEntity.getName().equals(AllergenEnumName.MILK)));
        assertTrue(all.stream().anyMatch(allergenEntity -> allergenEntity.getName().equals(AllergenEnumName.WHEAT)));

    }

    @Test
    public void createAllAllergensTest() {
        Arrays.stream(AllergenEnumName.values()).forEach(allergenEnumName -> {
            AllergenEntity allergenEntity = new AllergenEntity(allergenEnumName, allergenEnumName.getImageUrl());
            AllergenEntity saved = allergenRepository.save(allergenEntity);
            assertTrue(saved.getId() > 0);
        });
    }


    @Test
    void findAllOrderedByNameTest() {
        List<AllergenEntity> allOrderedByName = allergenRepository.findAllOrderedByName();
        int milk = 0;
        int wheat = 0;
        for (int i = 0; i < allOrderedByName.size() - 1; i++) {
            if (allOrderedByName.get(i).equals(allergen1)) {
                milk = i;
            }
            if (allOrderedByName.get(i).equals(allergen2)) {
                wheat = i;
            }

        }


        assertTrue(milk < wheat);
    }

    @Test
    void findByName() {
        if (allergenRepository.count() > 2) {
            allergenRepository.delete(allergen1);
            allergenRepository.delete(allergen2);
        }
        allergenRepository.findAll().forEach(
                allergenEntity -> assertEquals(allergenEntity, allergenRepository.findByName(allergenEntity.getName()).get())
        );
    }
}