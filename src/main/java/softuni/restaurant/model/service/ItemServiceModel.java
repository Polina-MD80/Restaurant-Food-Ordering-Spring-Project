package softuni.restaurant.model.service;

import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.entity.enums.TypeEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ItemServiceModel {
    private Long id;
    private String name;

    private TypeEnum type;

    private String manufacturer;

    private Set<CategoryEntity> categories = new HashSet<>();

    private PictureServiceModel picture;

    private Integer volume;

    private Integer weight;

    private BigDecimal price;

    private Set<ProductEntity> products = new HashSet<>();

    private String description;

    private boolean isActive;

    public ItemServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ItemServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemServiceModel setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemServiceModel setName(String name) {
        this.name = name;
        return this;
    }



    public TypeEnum getType() {
        return type;
    }

    public ItemServiceModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public PictureServiceModel getPicture() {
        return picture;
    }

    public ItemServiceModel setPicture(PictureServiceModel picture) {
        this.picture = picture;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public ItemServiceModel setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public ItemServiceModel setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public ItemServiceModel setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public ItemServiceModel setProducts(Set<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ItemServiceModel setActive(boolean active) {
        isActive = active;
        return this;
    }
}
