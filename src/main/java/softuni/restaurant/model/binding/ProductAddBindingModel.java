package softuni.restaurant.model.binding;

import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;
import softuni.restaurant.model.validator.UniqueCategoryName;
import softuni.restaurant.model.validator.UniqueProductName;
import softuni.restaurant.model.view.AllergenViewModel;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAddBindingModel {
    @NotBlank
    @UniqueProductName
    @Size(min = 2, max = 20, message = "The product name must contain at least 2 symbols")
    private String name;
    private Set<AllergenEnumName> allergens= new HashSet<>();

    public String getName() {
        return name;
    }

    public ProductAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<AllergenEnumName> getAllergens() {
        return allergens;
    }

    public ProductAddBindingModel setAllergens(Set<AllergenEnumName> allergens) {
        this.allergens = allergens;
        return this;
    }
}

