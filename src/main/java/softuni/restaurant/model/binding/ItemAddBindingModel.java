package softuni.restaurant.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.PictureEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.model.validator.UniqueProductName;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ItemAddBindingModel {
    @NotBlank
    @UniqueProductName
    @Size(min = 2, max = 30, message = "The item name must contain at least 2 symbols and max 30")
    private String name;
    @NotNull
    private TypeEnum type;
    private String producer;
    private Set<String> categories = new HashSet<>();
    private MultipartFile picture;
    @Positive
    private Integer volume;
    @Positive
    private Integer weight;
    @PositiveOrZero
    @NotBlank
    private BigDecimal price;
    private Set<String> products = new HashSet<>();
    private String description;
    @NotNull
    private boolean isActive;

    public ItemAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public ItemAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public ItemAddBindingModel setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public ItemAddBindingModel setCategories(Set<String> categories) {
        this.categories = categories;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public ItemAddBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public ItemAddBindingModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public ItemAddBindingModel setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public ItemAddBindingModel setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Set<String> getProducts() {
        return products;
    }

    public ItemAddBindingModel setProducts(Set<String> products) {
        this.products = products;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ItemAddBindingModel setActive(boolean active) {
        isActive = active;
        return this;
    }
}
