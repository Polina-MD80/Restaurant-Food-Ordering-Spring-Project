package softuni.restaurant.service;

import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.view.AllergenViewModel;

import java.util.List;

public interface AllergenService {
    void initAllergens();
    List<AllergenViewModel> allAllergensOrderedByName();

    AllergenEntity findByName(String name);
}
