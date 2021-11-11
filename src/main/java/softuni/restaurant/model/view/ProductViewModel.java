package softuni.restaurant.model.view;

import java.util.Set;

public class ProductViewModel {
    private String id;
    private String name;
    private Set<AllergenViewModel> allergens;

    public ProductViewModel() {
    }

    public String getId() {
        return id;
    }

    public ProductViewModel setId(String id) {
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

    public Set<AllergenViewModel> getAllergens() {
        return allergens;
    }

    public ProductViewModel setAllergens(Set<AllergenViewModel> allergens) {
        this.allergens = allergens;
        return this;
    }
}
