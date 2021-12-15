package softuni.restaurant.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.entity.enums.TypeEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ItemUpdateBindingModel {
    public Long id;
    @NotBlank
    @Size(min = 2, max = 30, message = "The item name must contain at least 2 symbols and max 30")
    private String name;
    @NotNull(message = "You must select type")
    private TypeEnum type;
    private String manufacturer;
    private Set<CategoryEntity> categories = new HashSet<>();
    private MultipartFile picture;
    @Positive
    private Integer volume;
    @Positive
    private Integer weight;
    @DecimalMin(value = "0.0", message = "Enter positive price")
    @NotNull(message = "Enter price")
    private BigDecimal price;
    private Set<ProductEntity> products = new HashSet<>();
    private String description;
    @NotNull(message = "You must select if the item is available.")
    private boolean active;

    public ItemUpdateBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public ItemUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public ItemUpdateBindingModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemUpdateBindingModel setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public ItemUpdateBindingModel setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public ItemUpdateBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public ItemUpdateBindingModel setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public ItemUpdateBindingModel setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemUpdateBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public ItemUpdateBindingModel setProducts(Set<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ItemUpdateBindingModel setActive(boolean active) {
        this.active = active;
        return this;
    }
}
