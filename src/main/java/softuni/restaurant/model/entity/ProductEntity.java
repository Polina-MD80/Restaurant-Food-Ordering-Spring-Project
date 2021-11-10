package softuni.restaurant.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany
    private List<AllergenEntity> allergens;
    public ProductEntity() {
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<AllergenEntity> getAllergens() {
        return allergens;
    }

    public ProductEntity setAllergens(List<AllergenEntity> allergens) {
        this.allergens = allergens;
        return this;
    }
}
