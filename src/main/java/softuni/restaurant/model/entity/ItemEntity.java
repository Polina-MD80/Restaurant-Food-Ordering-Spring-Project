package softuni.restaurant.model.entity;

import softuni.restaurant.model.entity.enums.TypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "items")
public  class ItemEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "items_categories",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();
    @OneToOne
    private PictureEntity picture;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;
    private String manufacturer;
    private Integer volume;
    private Integer weight;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "items_products",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductEntity> products = new HashSet<>();
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AllergenEntity> allergens;

    public ItemEntity() {
    }

    public void removeCategory(CategoryEntity catgeory){
        this.getCategories().remove(catgeory);
    }
    public void removeProduct(ProductEntity product){
        this.getProducts().remove(product);
    }

    public String getName() {
        return name;
    }

    public ItemEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemEntity setManufacturer(String producer) {
        this.manufacturer = producer;
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

    public Set<AllergenEntity> getAllergens() {
        return allergens;
    }

    public ItemEntity setAllergens(Set<AllergenEntity> allergens) {
        this.allergens = allergens;
        return this;
    }


    public Set<String> getCategoriesNames(){
        return this.categories.stream().map(CategoryEntity::getName).collect(Collectors.toSet());
    }
    public Set<String> getProductsByName(){
        return this.products.stream().map(ProductEntity::getName).collect(Collectors.toSet());
    }
    @PrePersist
    @PostUpdate
    public void collectAllergens(){
        this.allergens = new HashSet<>();
        Set<AllergenEntity> set = this.products.stream().flatMap(productEntity -> productEntity.getAllergens().stream()).collect(Collectors.toSet());
        this.setAllergens(set);

    }
}
