package softuni.restaurant.model.entity;

import softuni.restaurant.model.entity.enums.AllergenEnumName;

import javax.persistence.*;

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

}
