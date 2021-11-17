package softuni.restaurant.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.model.validator.UniqueItemName;
import softuni.restaurant.model.validator.UniqueProductName;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ItemAddBindingModel {
    @NotBlank
    @UniqueItemName
    @Size(min = 2, max = 30, message = "The item name must contain at least 2 symbols and max 30")
    private String name;
    @NotNull(message = "You must select type")
    private TypeEnum type;
    private String manufacturer;
    private Set<String> categories = new HashSet<>();
    private MultipartFile picture;
    @Positive
    private Integer volume;
    @Positive
    private Integer weight;
    @DecimalMin(value = "0.0", message = "Enter positive price")
    @NotNull(message = "Enter price")
    private BigDecimal price;
    private Set<String> products = new HashSet<>();
    private String description;
    @NotNull(message = "You must select if the item is available.")
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

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemAddBindingModel setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
