package softuni.restaurant.model.binding;

import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class ProductUpdateBindingModel {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "The product name must contain at least 2 symbols")
    private String name;
    private Set<AllergenEntity> allergens = new HashSet<>();

    public ProductUpdateBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<AllergenEntity> getAllergens() {
        return allergens;
    }

    public ProductUpdateBindingModel setAllergens(Set<AllergenEntity> allergens) {
        this.allergens = allergens;
        return this;
    }
}
