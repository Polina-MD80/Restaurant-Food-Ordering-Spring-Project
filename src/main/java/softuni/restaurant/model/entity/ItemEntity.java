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
    @ManyToOne
    private CategoryEntity category;
    @ManyToOne
    private PictureEntity picture;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;
    private Integer volume;
    private Integer weight;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToMany
    private Set<ProductEntity> product;
    private String description;
    @Column(nullable = false)
    private boolean isActive;

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public CategoryEntity getCategory() {
        return category;
    }

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

    public Set<ProductEntity> getProduct() {
        return product;
    }

    public ItemEntity setProduct(Set<ProductEntity> product) {
        this.product = product;
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
}
