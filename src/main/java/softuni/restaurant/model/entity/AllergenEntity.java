package softuni.restaurant.model.entity;

import softuni.restaurant.model.entity.enums.AllergenEnumName;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "allergens")
public class AllergenEntity extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllergenEnumName name;
    private String imageUrl;


    public AllergenEntity() {
    }

    public AllergenEntity(AllergenEnumName name, String image) {
        this.name = name;
        this.imageUrl = image;
    }

    public AllergenEntity(AllergenEnumName name) {
        this.name = name;
    }

    public AllergenEnumName getName() {
        return name;
    }

    public AllergenEntity setName(AllergenEnumName name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AllergenEntity setImageUrl(String image) {
        this.imageUrl = image;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllergenEntity)) return false;
        AllergenEntity that = (AllergenEntity) o;
        return name == that.name && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl);
    }
}
