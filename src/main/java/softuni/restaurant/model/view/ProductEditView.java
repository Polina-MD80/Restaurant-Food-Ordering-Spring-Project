package softuni.restaurant.model.view;

import softuni.restaurant.model.entity.AllergenEntity;


import java.util.HashSet;
import java.util.Set;

public class ProductEditView {
    private Long id;
    private String name;
    private Set<AllergenEntity> allergens = new HashSet<>();

    public ProductEditView() {
    }

    public Long getId() {
        return id;
    }

    public ProductEditView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEditView setName(String name) {
        this.name = name;
        return this;
    }

    public Set<AllergenEntity> getAllergens() {
        return allergens;
    }

    public ProductEditView setAllergens(Set<AllergenEntity> allergens) {
        this.allergens = allergens;
        return this;
    }
}
