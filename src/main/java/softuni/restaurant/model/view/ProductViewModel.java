package softuni.restaurant.model.view;

import softuni.restaurant.model.entity.AllergenEntity;

import java.util.Set;

public class ProductViewModel {
    private Long id;
    private String name;
    private Set<AllergenEntity> allergens;

    public ProductViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<AllergenEntity> getAllergens() {
        return allergens;
    }

    public ProductViewModel setAllergens(Set<AllergenEntity> allergens) {
        this.allergens = allergens;
        return this;
    }
}
