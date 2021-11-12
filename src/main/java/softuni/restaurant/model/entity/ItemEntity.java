package softuni.restaurant.model.entity;

import softuni.restaurant.model.entity.enums.TypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "items")
public  class ItemEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany
    private Set<CategoryEntity> categories;
    @OneToOne
    private PictureEntity picture;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;
    private Integer volume;
    private Integer weight;
    @Column(nullable = false)
    private BigDecimal price;
    @OneToMany
    private Set<ProductEntity> products;
    private String description;
    @Column(nullable = false)
    private boolean isActive;



    public String getName() {
        return name;
    }

    public ItemEntity setName(String name) {
        this.name = name;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public ItemEntity setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public ItemEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public ItemEntity setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public ItemEntity setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public ItemEntity setProducts(Set<ProductEntity> product) {
        this.products = product;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ItemEntity setActive(boolean active) {
        isActive = active;
        return this;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public ItemEntity setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }
}
