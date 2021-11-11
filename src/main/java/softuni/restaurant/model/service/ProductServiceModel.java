package softuni.restaurant.model.service;

import softuni.restaurant.model.entity.enums.AllergenEnumName;
import softuni.restaurant.model.view.AllergenViewModel;

import java.util.HashSet;
import java.util.Set;

public class ProductServiceModel {
    private Long id;
    private String name;
    private Set<AllergenEnumName> allergens = new HashSet<>();

    public ProductServiceModel() {
        this.allergens = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public ProductServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<AllergenEnumName> getAllergens() {
        return allergens;
    }

    public ProductServiceModel setAllergens(Set<AllergenEnumName> allergens) {
        this.allergens = allergens;
        return this;
    }
}
