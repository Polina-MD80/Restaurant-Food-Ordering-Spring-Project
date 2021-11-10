package softuni.restaurant.Service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.Repository.AllergenRepository;
import softuni.restaurant.Service.AllergenService;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;

import java.util.Arrays;

@Service
public class AllergenServiceImpl implements AllergenService {
    private final AllergenRepository allergensRepository;

    public AllergenServiceImpl(AllergenRepository allergensRepository) {
        this.allergensRepository = allergensRepository;
    }

    @Override
    public void initAllergens() {
        if (allergensRepository.count()==0){
            Arrays.stream(AllergenEnumName.values()).forEach(allergenEnumName -> {
                AllergenEntity allergenEntity = new AllergenEntity(allergenEnumName, allergenEnumName.getImageUrl());
                allergensRepository.save(allergenEntity);
            });
        }
    }
}
