package softuni.restaurant.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "categories")
@Entity
public class CategoryEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    private PictureEntity picture;
    private String description;

    public CategoryEntity() {
    }

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public CategoryEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }
}