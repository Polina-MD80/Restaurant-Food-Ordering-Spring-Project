package softuni.restaurant.service;

import softuni.restaurant.model.entity.AllergenEntity;

import java.util.List;

public interface AllergenService {
    void initAllergens();

    List<AllergenEntity> allAllergensOrderedByName();

    AllergenEntity findByName(String name);
}
